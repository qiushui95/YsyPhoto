package son.ysy.photo.ui.upload.select

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.yangcx.base.ext.parcelable
import me.yangcx.base.viewmodels.BaseViewModel
import me.yangcx.base.viewmodels.impls.RequestListDelegateVMImpl
import org.koin.core.inject
import son.ysy.photo.entities.ui.ImageInfo
import son.ysy.photo.repositories.LocalImageRepository

class UploadSelectViewModel(handle: SavedStateHandle) : BaseViewModel() {

    private val localImageRepository by inject<LocalImageRepository>()

    private val selectedIdList by lazy {
        mutableListOf<String>()
    }

    val imageFetchDelegate by lazy {
        RequestListDelegateVMImpl<ImageInfo>(
            handle,
            cancelBeforeRequest = true,
            waitForBeforeFinish = true
        )
    }

    fun startFetchImage() {
        viewModelScope.launch {
            imageFetchDelegate.doRequest {
                localImageRepository.loadLocalImage()
            }
        }
    }

    fun toggleSelect(imageId: String) {
        if (selectedIdList.contains(imageId)) {
            selectedIdList.remove(imageId)
        } else {
            selectedIdList.add(imageId)
        }
        imageFetchDelegate.doChangeData(
            imageFetchDelegate.getCurrentData() ?: emptyList<ImageInfo>().parcelable()
        )
    }

    fun getSelectIndex(imageInfo: ImageInfo) = selectedIdList.indexOf(imageInfo.id)
}