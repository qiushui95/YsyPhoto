package son.ysy.photo.dis

import androidx.lifecycle.SavedStateHandle
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import son.ysy.photo.ui.mine.HomeMineViewModel
import son.ysy.photo.ui.upload.select.UploadSelectViewModel

object ViewModelModule {
    val instance by lazy {
        module {
            viewModel { (handle: SavedStateHandle) ->
                HomeMineViewModel(handle)
            }
            viewModel { (handle: SavedStateHandle) ->
                UploadSelectViewModel(handle)
            }
        }
    }
}