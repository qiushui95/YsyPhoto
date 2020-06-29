package son.ysy.photo.ui.upload.select.items

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.constraintlayout.widget.ConstraintSet
import coil.api.load
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.paris.annotations.Styleable
import kotlinx.android.synthetic.main.item_upload_select.view.*
import me.yangcx.base.ext.click
import son.ysy.photo.R

@Styleable
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, fullSpan = false)
class ItemUploadImageView(context: Context) : MotionLayout(context) {

    companion object {
        private const val SCALE_SMALL = 0.8f
        private val ANIMATION_ID_START = View.generateViewId()
        private val ANIMATION_ID_END = View.generateViewId()
//        @Style(isDefault = true)
//        val background = itemLoadingFullViewStyle {
//            backgroundRes(R.color.colorFFFFFFFF)
//        }
    }

    private val originConstraintSet by lazy {
        ConstraintSet().apply {
            clone(this@ItemUploadImageView)
        }
    }

    init {
        View.inflate(context, R.layout.item_upload_select, this)
    }

    @ModelProp
    lateinit var imageId: String

    @ModelProp
    fun setImageUri(uri: Uri) {
        ivItemUploadSelect.load(uri)
    }

    @ModelProp
    fun setSelectIndex(index: Int) {
        tvItemUploadSelectPosition.text = "".takeIf {
            index < 0
        } ?: (index + 1).toString()

        if (index < 0) {
            transitionToStart()
        } else {
            transitionToEnd()
        }
    }

    @CallbackProp
    fun setClick(listener: OnClickListener?) {
        ivItemUploadSelect.click(duration = 0, listener = listener)
    }

    private fun resetMotionScene() {
        MotionScene(this)
            .apply {
                duration = 500
                val startConstraintSet = ConstraintSet().apply {
                    clone(originConstraintSet)
                }
                val endConstraintSet = ConstraintSet().apply {
                    clone(originConstraintSet)
                    setScaleX(R.id.ivItemUploadSelect,
                        SCALE_SMALL
                    )
                    setScaleY(R.id.ivItemUploadSelect,
                        SCALE_SMALL
                    )
                    setAlpha(R.id.tvItemUploadSelectPosition, 1f)
                }
                setConstraintSet(ANIMATION_ID_START, startConstraintSet)
                setConstraintSet(ANIMATION_ID_END, endConstraintSet)
            }
            .apply {
                setScene(this)
            }
        setTransition(
            ANIMATION_ID_START,
            ANIMATION_ID_END
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        resetMotionScene()
    }
}