package son.ysy.photo.ui.upload.select

import android.content.Context
import android.net.Uri
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
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
//        @Style(isDefault = true)
//        val background = itemLoadingFullViewStyle {
//            backgroundRes(R.color.colorFFFFFFFF)
//        }
    }

    init {
        View.inflate(context, R.layout.item_upload_select, this)
    }


    @ModelProp
    fun setImageId(imageId: String) {

    }

    @ModelProp
    fun setImageUri(uri: Uri) {
        ivItemUploadSelect.load(uri) {
            crossfade(500)
        }
    }

    @ModelProp
    fun setSelectIndex(index: Int) {
        tvItemUploadSelectPosition.text = (index + 1).toString()
        if (index < 0) {
            transitionToStart()
        } else {
            transitionToEnd()
        }
    }

    @CallbackProp
    fun setClick(listener: OnClickListener?) {
        ivItemUploadSelect.click(listener = listener)
    }
}