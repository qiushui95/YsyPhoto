package me.yangcx.base.init

import android.app.Application
import com.effective.android.anchors.Task

class StethoInitTask(
    private val application: Application
) : Task(TASK_NAME, true) {
    companion object {
        const val TASK_NAME = "stetho"
    }

    override fun run(name: String) {

    }
}