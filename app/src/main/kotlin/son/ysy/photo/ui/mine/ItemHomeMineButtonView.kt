package son.ysy.photo.ui.mine

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.paris.annotations.Style
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.extensions.background
import com.airbnb.paris.extensions.itemHomeMineButtonViewStyle
import com.airbnb.paris.extensions.layoutHeightDp
import com.airbnb.paris.extensions.layoutWidth
import com.blankj.utilcode.util.ConvertUtils
import com.noober.background.drawable.DrawableCreator
import kotlinx.android.synthetic.main.item_home_mine_button.view.*
import son.ysy.photo.R


@Styleable
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, fullSpan = true)
class ItemHomeMineButtonView(context: Context) : ConstraintLayout(context) {
    companion object {
        @Style(isDefault = true)
        val background = itemHomeMineButtonViewStyle {
            background(
                DrawableCreator.Builder()
                    .setSolidColor(Color.WHITE)
                    .setRipple(true, Color.GRAY)
                    .build()
            )
            layoutWidth(ViewGroup.LayoutParams.MATCH_PARENT)
            layoutHeightDp(48)
        }
    }

    init {
        View.inflate(context, R.layout.item_home_mine_button, this)
    }

    @ModelProp
    fun setIconRes(@DrawableRes iconRes: Int) {
        ivHomeMineButtonIcon.setImageResource(iconRes)
    }

    @ModelProp
    fun setTipRes(@StringRes tipRes: Int) {
        tvHomeMineButtonTip.setText(tipRes)
    }

    @ModelProp
    fun setMarginTop(marginTop: Int) {
        updateLayoutParams<MarginLayoutParams> {
            updateMargins(top = ConvertUtils.dp2px(marginTop * 1f))
        }
    }
}