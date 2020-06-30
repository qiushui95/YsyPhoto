package son.ysy.photo.ui.upload.select.items

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.paris.annotations.Styleable
import kotlinx.android.synthetic.main.item_upload_select.view.*
import me.yangcx.base.ext.click
import me.yangcx.base.ext.load
import son.ysy.photo.R

@Styleable
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, fullSpan = false)
class ItemUploadImageView(context: Context) : ConstraintLayout(context) {

    companion object {
        private const val ALPHA_LARGE = 1f
        private const val ALPHA_SMALL = 0f
    }

    @ModelProp
    lateinit var imageId: String

    init {
        View.inflate(context, R.layout.item_upload_select, this)
    }

    @ModelProp
    fun setImageUri(uri: Uri) {
        ivItemUploadSelect.load(uri) {
            placeholder(R.mipmap.ic_launcher)
        }
    }

    @ModelProp
    fun setSelectIndex(index: Int) {
        tvItemUploadSelectPosition.text = "".takeIf {
            index < 0
        } ?: (index + 1).toString()
        tvItemUploadSelectPosition.alpha = ALPHA_SMALL.takeIf { index < 0 } ?: ALPHA_LARGE
    }

    @CallbackProp
    fun setClick(listener: OnClickListener?) {
        ivItemUploadSelect.click(duration = 0, listener = listener)
    }
}