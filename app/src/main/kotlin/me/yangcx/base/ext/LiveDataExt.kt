package me.yangcx.base.ext

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

@MainThread
inline fun <T> LiveData<T>.observeViewLifecycle(
    fragment: Fragment,
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    return observe(fragment.viewLifecycleOwner, onChanged)
}