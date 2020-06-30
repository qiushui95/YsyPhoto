package son.ysy.photo.ui.upload.apply

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.OnModelClickListener
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.fragment_upload_apply.*
import kotlinx.coroutines.*
import me.yangcx.base.annotations.BindLayoutRes
import me.yangcx.base.entities.RequestStatus
import me.yangcx.base.ext.observeViewLifecycle
import me.yangcx.base.fragments.BaseFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.parameter.parametersOf
import son.ysy.photo.R
import son.ysy.photo.entities.response.ResponseTokenInfo


@BindLayoutRes(R.layout.fragment_upload_apply)
class UploadApplyFragment : BaseFragment() {

    private val args by navArgs<UploadApplyFragmentArgs>()

    private val viewModel by stateViewModel<UploadApplyViewModel>(parameters = { parametersOf(args.images) })
    private val buildModelJob = SupervisorJob()

    private lateinit var requestStatusObserver: Observer<RequestStatus>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestStatusObserver = viewModel.uploadTokenDelegate
            .requestStatusLiveDistinct
            .observeViewLifecycle(this) {
                it.buildRvModel<ResponseTokenInfo>(
                    rvUploadApply,
                    retryClick = viewModel.uploadTokenDelegate::retry
                ) {
                    viewModel.uploadTokenDelegate
                        .requestStatusLiveDistinct
                        .removeObserver(requestStatusObserver)
                    viewModel.uploadInfoListDelegate
                        .dataDistinct.observeViewLifecycle(this) {
                            viewLifecycleOwner.lifecycleScope
                                .launchWhenResumed {
                                    buildModelJob.cancelChildren()
                                    launch(buildModelJob) {
                                        LogUtils.e(it.value)
                                        withContext(Dispatchers.IO) {
                                            it.value
                                                .map { uploadInfo ->
                                                    val clickListener = if (uploadInfo.hasError) {
                                                        OnModelClickListener<ItemUploadApplyViewModel_, ItemUploadApplyView> { model, _, _, _ ->
                                                            viewModel.retry(model.imageId())
                                                        }
                                                    } else {
                                                        null
                                                    }
                                                    ItemUploadApplyViewModel_()
                                                        .id(uploadInfo.imageInfo.id)
                                                        .imageId(uploadInfo.imageInfo.id)
                                                        .imageUri(uploadInfo.imageInfo.uri)
                                                        .tip(uploadInfo.itemTip)
                                                        .click(clickListener)
                                                }
                                        }.apply {
                                            rvUploadApply.setModels(this)
                                        }
                                    }
                                }

                        }
                }
            }
    }
}