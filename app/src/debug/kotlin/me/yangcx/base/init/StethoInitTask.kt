package me.yangcx.base.init

import android.app.Application
import com.effective.android.anchors.Task
import com.facebook.stetho.Stetho

class StethoInitTask(
    private val application: Application
) : Task(TASK_NAME, true) {
    companion object {
        const val TASK_NAME = "stetho"
    }

    override fun run(name: String) {
        Stetho.initializeWithDefaults(application)
    }
}