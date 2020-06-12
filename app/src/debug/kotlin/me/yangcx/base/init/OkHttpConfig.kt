package me.yangcx.base.init

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun OkHttpClient.Builder.configDebug(
    application: Application
) = this.addInterceptor(ChuckerInterceptor(application))
    .addNetworkInterceptor(StethoInterceptor())
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })