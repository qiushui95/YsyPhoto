package me.yangcx.base.init

import android.app.Application
import okhttp3.OkHttpClient

fun OkHttpClient.Builder.configDebug(
    application: Application
) = this