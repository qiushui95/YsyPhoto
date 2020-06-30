package son.ysy.photo.ui.upload.select

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.SimpleTransitionListener
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_upload_select.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.yangcx.base.annotations.BindLayoutRes
import me.yangcx.base.ext.observeViewLifecycle
import me.yangcx.base.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import son.ysy.photo.R
import son.ysy.photo.ui.upload.select.items.ItemUploadImageDisplayHorizontalViewModel_
import son.ysy.photo.ui.upload.select.items.ItemUploadImageViewModel_

@BindLayoutRes(R.layout.fragment_upload_select)
class UploadSelectFragment : BaseFragment() {

    private val viewModel by stateViewModel<UploadSelectViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionUtils.permission(PermissionConstants.STORAGE)
            .callback { isAllGranted, _, _, _ ->
                if (isAllGranted) {
                    loadLocalImage()
                } else {
                    ToastUtils.cancel()
                    ToastUtils.showLong(R.string.string_common_no_permission)
                }
            }
            .request()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mlUploadSelect.setTransitionListener(object : SimpleTransitionListener() {
            override fun onTransitionCompleted(motionLayout: MotionLayout, stateId: Int) {
                BarUtils.setStatusBarVisibility(
                    requireActivity(),
                    stateId == R.id.constraintSetUploadSelectStart
                )
            }
        })
        viewModel.imageFetchDelegate
            .dataLive
            .observeViewLifecycle(this) {
                lifecycleScope.launchWhenResumed {
                    withContext(Dispatchers.IO) {
                        it.value
                            .map { imageInfo ->
                                ItemUploadImageViewModel_()
                                    .id(imageInfo.id)
                                    .imageId(imageInfo.id)
                                    .imageUri(imageInfo.uri)
                                    .selectIndex(viewModel.getSelectIndex(imageInfo))
                                    .click { model, _, _, _ ->
                                        viewModel.toggleSelect(model.imageId())
                                    }
                            }
                    }.apply {
                        rvUploadSelect.setModels(this)
                    }
                }
            }
        viewModel.selectedCount
            .observeViewLifecycle(this) {
                btnUploadSelectContinue.text = StringUtils.getString(
                    R.string.string_upload_select_continue_format,
                    it
                )
                btnUploadSelectContinue.isEnabled = it > 0
            }
        viewModel.selectedList
            .observeViewLifecycle(this) {
                lifecycleScope.launchWhenResumed {
                    withContext(Dispatchers.IO) {
                        it.map { imageInfo ->
                            ItemUploadImageDisplayHorizontalViewModel_().apply {
                                id(javaClass.name, imageInfo.id)
                                imageUri(imageInfo.uri)
                                imageId(imageInfo.id)
                                click { model, _, _, _ ->
                                    viewModel.getImageIndex(model.imageId())?.also { index ->
                                        rvUploadSelect.smoothScrollToPosition(index)

                                    }
                                }
                            }
                        }
                    }.apply {
                        rvUploadDisplay.setModels(this)
                    }
                }
            }
    }

    private fun loadLocalImage() {
        viewModel.startFetchImage()
    }
}