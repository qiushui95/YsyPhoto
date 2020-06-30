package son.ysy.photo.ui.mine

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.paris.annotations.Style
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.extensions.backgroundRes
import com.airbnb.paris.extensions.itemHomeMineUserInfoViewStyle
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.item_home_mine_user_info.view.*
import me.yangcx.base.ext.load
import son.ysy.photo.R


@Styleable
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, fullSpan = true)
class ItemHomeMineUserInfoView(context: Context) : ConstraintLayout(context) {
    companion object {
        @Style(isDefault = true)
        val background = itemHomeMineUserInfoViewStyle {
            backgroundRes(R.color.colorFFFFFFFF)
        }
    }

    init {
        View.inflate(context, R.layout.item_home_mine_user_info, this)
    }

    @ModelProp
    fun setAvatarUrl(avatarUrl: String) {
        ivItemHomeMineUserInfoAvatar.load(avatarUrl) {
            transition(DrawableTransitionOptions.withCrossFade(300))
            circleCrop()
        }
    }

    @ModelProp
    fun setPhone(phone: String) {
        tvItemHomeMineUserInfoPhone.text = phone
    }

    @ModelProp
    fun setRelationship(relationship: String) {
        tvItemHomeMineUserInfoRelationship.text = relationship
    }
}