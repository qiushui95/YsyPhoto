package son.ysy.photo.ui.upload.apply

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import kotlinx.android.synthetic.main.item_upload_apply.view.*
import me.yangcx.base.ext.click
import me.yangcx.base.ext.gone
import me.yangcx.base.ext.load
import me.yangcx.base.ext.visible
import son.ysy.photo.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, fullSpan = false)
class ItemUploadApplyView(context: Context) : ConstraintLayout(context) {
    init {
        View.inflate(context, R.layout.item_upload_apply, this)
    }

    @ModelProp
    lateinit var imageId: String

    @ModelProp
    fun setImageUri(imageUri: Uri) {
        ivItemUploadApply.load(imageUri)
    }

    @TextProp
    fun setTip(tip: CharSequence) {
        tvItemUploadTip.text = tip
        if (tip.isBlank()) {
            gone()
        } else {
            visible()
        }
    }

    @CallbackProp
    fun setClick(listener: OnClickListener?) {
        click(listener = listener)
    }
}