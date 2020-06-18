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
import com.airbnb.paris.extensions.itemNotLoginFullViewStyle
import kotlinx.android.synthetic.main.item_not_login_full.view.*
import me.yangcx.base.ext.click
import son.ysy.photo.R

@Styleable
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT, fullSpan = true)
class ItemNotLoginFullView(context: Context) : ConstraintLayout(context) {
    companion object {
        @Style(isDefault = true)
        val background = itemNotLoginFullViewStyle {
            backgroundRes(R.color.colorFFFFFFFF)
        }
    }

    init {
        View.inflate(context, R.layout.item_not_login_full, this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lottieItemNotLogin.playAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lottieItemNotLogin.cancelAnimation()
    }

    @TextProp
    fun setLoginTip(tip: CharSequence) {
        tvItemNotLoginTip.text = tip
    }

    @CallbackProp
    fun setLoginClick(click: (() -> Unit)?) {
        btnItemNotLogin.click(block = click)
    }
}