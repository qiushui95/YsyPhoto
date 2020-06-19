package son.ysy.photo.ui.items

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.airbnb.paris.annotations.Style
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.extensions.backgroundRes
import com.airbnb.paris.extensions.itemLoadingFullViewStyle
import kotlinx.android.synthetic.main.item_error_full.view.*
import me.yangcx.base.ext.click
import son.ysy.photo.R


@Styleable
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT, fullSpan = true)
class ItemErrorFullView(context: Context) : ConstraintLayout(context) {
    companion object {
        @Style(isDefault = true)
        val background = itemLoadingFullViewStyle {
            backgroundRes(R.color.colorFFFFFFFF)
        }
    }

    init {
        View.inflate(context, R.layout.item_error_full, this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lottieItemErrorFull.playAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lottieItemErrorFull.cancelAnimation()
    }

    @TextProp
    fun setErrorMessage(errorMessage: CharSequence) {
        tvItemErrorFullTip.text = errorMessage
    }

    @CallbackProp
    fun setRetryClick(click: (() -> Unit)?) {
        btnItemErrorFullRetry.click(block = click)
    }
}