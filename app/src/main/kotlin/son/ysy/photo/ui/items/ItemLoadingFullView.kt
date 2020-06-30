package son.ysy.photo.ui.items

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.airbnb.paris.annotations.Style
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.extensions.backgroundRes
import com.airbnb.paris.extensions.itemLoadingFullViewStyle
import kotlinx.android.synthetic.main.item_loading_full.view.*
import son.ysy.photo.R


@Styleable
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT, fullSpan = true)
class ItemLoadingFullView(context: Context) : ConstraintLayout(context) {
    companion object {
        @Style(isDefault = true)
        val background = itemLoadingFullViewStyle {
            backgroundRes(R.color.colorFFFFFFFF)
        }
    }

    init {
        View.inflate(context, R.layout.item_loading_full, this)
    }

    @TextProp
    fun setLoadingMessage(loadingMessage: CharSequence) {
        tvItemLoadingFullTip.text = loadingMessage
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lottieItemLoadingFull.playAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lottieItemLoadingFull.cancelAnimation()
    }
}