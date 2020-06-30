package son.ysy.photo.ui.upload.apply

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.PathUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.yangcx.base.ext.copyToFile
import me.yangcx.base.ext.parcelable
import me.yangcx.base.ext.sendIfOpen
import me.yangcx.base.viewmodels.BaseViewModel
import me.yangcx.base.viewmodels.impls.DataListLiveDelegateVMImpl
import me.yangcx.base.viewmodels.impls.RequestDelegateVMImpl
import org.koin.core.inject
import son.ysy.photo.entities.enums.ImageType
import son.ysy.photo.entities.response.ResponseTokenInfo
import son.ysy.photo.entities.ui.ImageInfo
import son.ysy.photo.entities.ui.UploadInfo
import son.ysy.photo.repositories.UploadRepository
import java.io.File

class UploadApplyViewModel(
    handle: SavedStateHandle,
    imageInfoArray: Array<ImageInfo>
) : BaseViewModel() {
    private companion object {
        const val MAX_DEALER_COUNT = 4
    }

    private val uploadDir by lazy {
        File(PathUtils.getCachePathExternalFirst(), "upload")
    }

    private val uploadRepository by inject<UploadRepository>()

    val uploadTokenDelegate by lazy {
        RequestDelegateVMImpl<ResponseTokenInfo>(
            handle,
            cancelCurrentIfBusy = true
        )
    }

    val uploadInfoListDelegate by lazy {
        DataListLiveDelegateVMImpl<UploadInfo>(handle)
    }

    private val updateListChannel by lazy {
        Channel<UploadInfo>(Channel.UNLIMITED)
    }

    private val copyToCacheChannel by lazy {
        Channel<UploadInfo>(Channel.UNLIMITED)
    }

    private val md5Channel by lazy {
        Channel<UploadInfo>(Channel.UNLIMITED)
    }

    private val uploadCheckChannel by lazy {
        Channel<UploadInfo>(Channel.UNLIMITED)
    }

    private val qiNiuChannel by lazy {
        Channel<UploadInfo>(Channel.UNLIMITED)
    }

    private val uploadToServerChannel by lazy {
        Channel<UploadInfo>(Channel.UNLIMITED)
    }

    init {
        getUploadToken()
        viewModelScope.launch {
            launch(Dispatchers.IO) {
                uploadTokenDelegate
                    .dataFlow
                    .collect { tokenInfo ->
                        val list = imageInfoArray.map {
                            UploadInfo(it, namePrefix = tokenInfo.prefix)
                        }
                        launch(Dispatchers.Main) {
                            uploadInfoListDelegate.changeData(list.parcelable())
                        }.join()
                        launch(Dispatchers.IO) {
                            list.forEach {
                                it.sendNextStep()
                            }
                        }
                    }
            }
            launch(Dispatchers.Main) {
                for (uploadInfo in updateListChannel) {
                    withContext(Dispatchers.IO) {
                        (uploadInfoListDelegate.getCurrentData()
                            ?.value
                            ?.map { oldItem ->
                                oldItem.takeUnless {
                                    it.imageInfo.id == uploadInfo.imageInfo.id
                                } ?: uploadInfo
                            } ?: emptyList())
                            .parcelable()
                    }.apply {
                        uploadInfoListDelegate.changeData(this)
                    }
                }
            }
            launch {
                repeat(MAX_DEALER_COUNT) {
                    receiveCopyChannel()
                }
            }
            launch {
                repeat(MAX_DEALER_COUNT) {
                    receiveMd5Channel()
                }
            }
        }
    }

    private fun getUploadToken() {
        viewModelScope.launch {
            uploadTokenDelegate.doRequest(viewModelScope) {
                uploadRepository.getUploadToken(ImageType.TYPE_PHOTO)
            }
        }
    }


    fun retry(imageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getUploadInfoByImageId(imageId)?.sendNextStep()
        }
    }

    private fun getUploadInfoByImageId(
        imageId: String
    ) = uploadInfoListDelegate.getCurrentData()
        ?.value
        ?.firstOrNull { it.imageInfo.id == imageId }

    private suspend fun sendDealingChannel(uploadInfo: UploadInfo) {
        uploadInfo.copy(isDealing = true).sendUpdateChannel()
    }

    private suspend fun UploadInfo.sendUpdateChannel(): UploadInfo {
        if (!updateListChannel.isClosedForSend) {
            updateListChannel.sendIfOpen(this)
        }
        return this
    }

    private suspend fun UploadInfo.sendNextStep(): UploadInfo {
        if (localFile == null) {
            copyToCacheChannel.sendIfOpen(this)
        } else if (md5 == null) {
            md5Channel.sendIfOpen(this)
        } else if (canUpload == null || namePrefix == null) {
            uploadCheckChannel.sendIfOpen(this)
        } else if (qiNiuProgress == null) {
            qiNiuChannel.sendIfOpen(this)
        } else if (!hasUploadToServer) {
            uploadToServerChannel.sendIfOpen(this)
        }
        return this
    }

    private fun CoroutineScope.receiveCopyChannel() {
        launch(Dispatchers.IO) {
            for (uploadInfo in copyToCacheChannel) {
                if (uploadInfo.localFile != null) {
                    uploadInfo.sendNextStep()
                } else {
                    val localFile = File(uploadDir, uploadInfo.localFileName)
                    try {
                        sendDealingChannel(uploadInfo)
                        uploadInfo.imageInfo.uri.copyToFile(localFile)
                        uploadInfo.copy(localFile = localFile)
                            .sendUpdateChannel()
                            .sendNextStep()
                    } catch (e: Exception) {
                        uploadInfo.copy(hasError = true)
                            .sendUpdateChannel()
                    }
                }
            }
        }
    }

    private fun CoroutineScope.receiveMd5Channel() {
        launch(Dispatchers.IO) {
            for (uploadInfo in md5Channel) {
                if (uploadInfo.md5 != null) {
                    uploadInfo.sendNextStep()
                } else if (uploadInfo.localFile != null) {
                    try {
                        sendDealingChannel(uploadInfo)
                        val md5 = EncryptUtils.encryptMD5File2String(uploadInfo.localFile)
                        uploadInfo.copy(md5 = md5)
                            .sendUpdateChannel()
                            .sendNextStep()
                    } catch (e: Exception) {
                        uploadInfo.copy(hasError = true)
                            .sendUpdateChannel()
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        uploadDir.listFiles()
            ?.forEach {
                it.delete()
            }
        updateListChannel.close()
        copyToCacheChannel.close()
        md5Channel.close()
        uploadCheckChannel.close()
        qiNiuChannel.close()
        uploadToServerChannel.close()
    }
}