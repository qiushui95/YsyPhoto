package me.yangcx.base.dis

import org.koin.dsl.module

object ViewModelModule {
    val instance by lazy {
        module {
//            viewModel { (handle: SavedStateHandle) ->
//                RegisterViewModel(handle)
//            }
        }
    }
}