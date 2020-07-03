package son.ysy.lib.base.exception

class UndefineInitTaskException(
    taskName: String
) : RuntimeException("task name:${taskName} not define!!"), MessageThrowable