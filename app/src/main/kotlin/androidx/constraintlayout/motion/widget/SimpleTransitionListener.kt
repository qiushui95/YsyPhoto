package androidx.constraintlayout.motion.widget

abstract class SimpleTransitionListener : MotionLayout.TransitionListener {
    override fun onTransitionTrigger(motionLayout: MotionLayout, p1: Int, p2: Boolean, p3: Float) {

    }

    override fun onTransitionStarted(
        motionLayout: MotionLayout,
        startStateId: Int,
        endStateId: Int
    ) {

    }

    override fun onTransitionChange(
        motionLayout: MotionLayout,
        startStateId: Int,
        endStateId: Int,
        progress: Float
    ) {

    }

    override fun onTransitionCompleted(motionLayout: MotionLayout, stateId: Int) {

    }
}