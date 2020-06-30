package son.ysy.photo.ui.upload.select

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import me.yangcx.base.alias.LiveListData
import me.yangcx.base.alias.MutableLiveListData
import me.yangcx.base.ext.parcelable
import me.yangcx.base.viewmodels.BaseViewModel
import me.yangcx.base.viewmodels.impls.RequestListDelegateVMImpl
import org.koin.core.inject
import son.ysy.photo.entities.ui.ImageInfo
import son.ysy.photo.repositories.LocalImageRepository

class UploadSelectViewModel(handle: SavedStateHandle) : BaseViewModel() {

    private val localImageRepository by inject<LocalImageRepository>()

    val imageFetchDelegate by lazy {
        RequestListDelegateVMImpl<ImageInfo>(
            handle,
            cancelBeforeRequest = true,
            waitForBeforeFinish = true
        )
    }

    private val _selectedList by lazy {
        MutableLiveListData<ImageInfo>(emptyList())
    }

    val selectedList: LiveListData<ImageInfo> by lazy {
        _selectedList.distinctUntilChanged()
    }

    val selectedCount by lazy {
        _selectedList.map {
            it.size
        }.distinctUntilChanged()
    }

    fun startFetchImage() {
        imageFetchDelegate.doRequest(viewModelScope) {
            localImageRepository.loadLocalImage()
        }
    }

    fun toggleSelect(imageId: String) {
        val allList = imageFetchDelegate.getCurrentData()?.value ?: emptyList()
        val imageInfo = allList.firstOrNull {
            it.id == imageId
        } ?: return
        val currentList = (_selectedList.value ?: emptyList()).toMutableList()
        if (currentList.contains(imageInfo)) {
            currentList.remove(imageInfo)
        } else {
            currentList.add(imageInfo)
        }
        imageFetchDelegate.doChangeData(allList.parcelable())
        _selectedList.value = currentList
    }

    fun getSelectIndex(
        imageInfo: ImageInfo
    ) = (_selectedList.value ?: emptyList()).indexOf(imageInfo)

    fun getImageIndex(imageId: String) = imageFetchDelegate.getCurrentData()
        ?.value
        ?.indexOfFirst { it.id == imageId }
        ?.takeUnless { it < 0 }
}