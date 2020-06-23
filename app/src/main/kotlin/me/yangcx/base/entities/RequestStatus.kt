package me.yangcx.base.entities

import android.os.Parcelable

sealed class RequestStatus {
    object Loading : RequestStatus()

    data class Success(val data: Parcelable) : RequestStatus() {

        inline fun <reified DATA : Parcelable> data() = data as? DATA
    }

   data class Error(val error: Throwable) : RequestStatus()
}