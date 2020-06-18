package me.yangcx.base.exceptions

class UndefineInitTaskException(
    taskName: String
) : RuntimeException("task name:${taskName} not define!!"), MessageThrowable