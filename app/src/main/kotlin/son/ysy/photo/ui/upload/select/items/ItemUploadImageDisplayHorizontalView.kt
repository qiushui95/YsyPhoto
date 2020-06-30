package son.ysy.photo.ui.upload.select.items

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import kotlinx.android.synthetic.main.item_upload_select_display_horizontal.view.*
import me.yangcx.base.ext.click
import me.yangcx.base.ext.load
import son.ysy.photo.R

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_MATCH_HEIGHT, fullSpan = false)
class ItemUploadImageDisplayHorizontalView(context: Context) : ConstraintLayout(context) {

    init {
        View.inflate(context, R.layout.item_upload_select_display_horizontal, this)
    }


    @ModelProp
    lateinit var imageId: String

    @ModelProp
    fun setImageUri(uri: Uri) {
        ivItemUploadSelectDisplay.load(uri)
    }


    @CallbackProp
    fun setClick(listener: OnClickListener?) {
        ivItemUploadSelectDisplay.click(duration = 0, listener = listener)
    }
}