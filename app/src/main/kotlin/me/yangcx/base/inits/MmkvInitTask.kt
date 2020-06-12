package me.yangcx.base.inits

import android.app.Application
import com.effective.android.anchors.Task
import com.tencent.mmkv.MMKV

class MmkvInitTask(private val application: Application) : Task(TASK_NAME) {
    companion object {
        const val TASK_NAME = "mmkv"
    }

    override fun run(name: String) {
        MMKV.initialize(application)
    }
}