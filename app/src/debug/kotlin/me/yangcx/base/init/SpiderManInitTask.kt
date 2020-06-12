package me.yangcx.base.init

import android.app.Application
import com.effective.android.anchors.Task
import com.simple.spiderman.SpiderMan

class SpiderManInitTask(
    private val application: Application
) : Task(TASK_NAME, true) {
    companion object {
        const val TASK_NAME = "spiderMan"
    }

    override fun run(name: String) {
        SpiderMan.init(application)
    }
}