package me.yangcx.base.entities

import android.os.Parcelable
import androidx.annotation.StringRes
import com.airbnb.epoxy.EpoxyRecyclerView
import son.ysy.photo.R
import son.ysy.photo.ext.getShowMessage
import son.ysy.photo.ui.items.ItemErrorFullViewModel_
import son.ysy.photo.ui.items.ItemLoadingFullViewModel_

sealed class RequestStatus {

    object Loading : RequestStatus()

    data class Success(val data: Parcelable) : RequestStatus() {

        inline fun <reified DATA : Parcelable> data() = data as? DATA
    }

    data class Error(val error: Throwable) : RequestStatus()

    inline fun <reified DATA : Parcelable> buildRvModel(
        targetRv: EpoxyRecyclerView,
        @StringRes loadingMessage: Int = R.string.string_common_loading,
        noinline retryClick: () -> Unit,
        successBlock: (DATA) -> Unit
    ) {
        when (this@RequestStatus) {
            Loading -> targetRv.withModels {
                ItemLoadingFullViewModel_()
                    .id(ItemLoadingFullViewModel_::class.qualifiedName)
                    .loadingMessage(loadingMessage)
                    .addTo(this)
            }
            is Error -> targetRv.withModels {
                ItemErrorFullViewModel_()
                    .id(ItemErrorFullViewModel_::class.qualifiedName)
                    .errorMessage(this@RequestStatus.error.getShowMessage())
                    .retryClick(retryClick)
                    .addTo(this)
            }
            is Success -> {
                data<DATA>()?.apply(successBlock)
            }
        }
    }
}