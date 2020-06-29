package me.yangcx.base.widgets

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import androidx.core.view.updateLayoutParams
import androidx.navigation.findNavController
import com.airbnb.paris.annotations.Attr
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.extensions.style
import com.blankj.utilcode.util.BarUtils
import me.yangcx.base.ext.click
import me.yangcx.base.utils.ColorUtil
import son.ysy.photo.R
import son.ysy.photo.ui.MainActivity

@Styleable("YsyTitleView")
class YsyTitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val backImageButtonId by lazy {
        View.generateViewId()
    }

    private val backImageButton by lazy {
        ImageView(context).apply {
            id = backImageButtonId
            scaleType = ImageView.ScaleType.FIT_CENTER
            setImageResource(R.drawable.ic_back)
        }
    }

    private val centerTitleView by lazy {
        TextView(context).apply {
            id = View.generateViewId()
            includeFontPadding = false
        }
    }

    init {
        addView(backImageButton, LayoutParams(0, 0).apply {
            startToStart = LayoutParams.PARENT_ID
            topToTop = LayoutParams.PARENT_ID
            bottomToBottom = LayoutParams.PARENT_ID
            dimensionRatio = "1:1"
        })
        addView(
            centerTitleView,
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                startToEnd = backImageButtonId
                topToTop = backImageButtonId
                bottomToBottom = LayoutParams.PARENT_ID
                endToEnd = LayoutParams.PARENT_ID
            })
        style(attrs)

        backImageButton.click {
            if (context is MainActivity) {
                context.findNavController(R.id.fragmentContainerMain)
                    .navigateUp()
            }
        }
    }

    @Attr(
        R.styleable.YsyTitleView_title_height,
        defaultValue = R.dimen.dimen_title_view_height_default
    )
    fun backHeight(@Px titleHeight: Int) {
        backImageButton.updateLayoutParams<LayoutParams> {
            height = titleHeight
        }
        backImageButton.setPadding(titleHeight / 4)
        centerTitleView.updateLayoutParams<LayoutParams> {
            marginEnd = titleHeight
        }
    }

    @Attr(
        R.styleable.YsyTitleView_android_background,
        defaultValue = R.color.colorFFFFFFFF
    )
    fun background(backgroundDrawable: Drawable) {
        background = backgroundDrawable
        val context = context
        if (backgroundDrawable is ColorDrawable && context is Activity) {
            BarUtils.setStatusBarLightMode(context, !ColorUtil.isDarkColor(backgroundDrawable.color))
        }
    }

    @Attr(R.styleable.YsyTitleView_android_text)
    fun centerTitleText(title: CharSequence) {
        centerTitleView.text = title
    }

    @Attr(R.styleable.YsyTitleView_android_textColor, defaultValue = R.color.colorFF333333)
    fun centerTitleColor(@ColorInt textColor: Int) {
        centerTitleView.setTextColor(textColor)
    }

    @Attr(
        R.styleable.YsyTitleView_android_textSize,
        defaultValue = R.dimen.dimen_title_view_text_size_default
    )
    fun centerTitleSize(@Px textSize: Int) {
        centerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize * 1f)
    }

    @Attr(
        R.styleable.YsyTitleView_android_fitsSystemWindows,
        defaultValue = R.bool.bool_true
    )
    fun fitsSystemWindows(fitsSystemWindows: Boolean) {
        if (fitsSystemWindows) {
            backImageButton.updateLayoutParams<LayoutParams> {
                topMargin = BarUtils.getStatusBarHeight()
            }
        }
    }
}