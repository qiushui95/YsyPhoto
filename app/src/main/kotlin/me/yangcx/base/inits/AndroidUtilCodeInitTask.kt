package me.yangcx.base.inits

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.effective.android.anchors.Task

class AndroidUtilCodeInitTask(private val application: Application) : Task(TASK_NAME, true) {
    companion object {
        const val TASK_NAME = "androidUtilCode"
    }

    override fun run(name: String) {
        Utils.init(application)
    }
}