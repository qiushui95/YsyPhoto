package me.yangcx.base.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import me.yangcx.base.annotations.BindLayoutRes
import me.yangcx.base.exceptions.LayoutException

abstract class BaseFragment : Fragment() {

    protected open val lifecycleObservers by lazy {
        emptyList<LifecycleObserver>()
    }

    private fun getLayoutRes() = javaClass.getAnnotation(BindLayoutRes::class.java)
        ?.layoutRes
        ?.takeIf {
            it != 0
        } ?: throw LayoutException(this::class)

    protected open fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = try {
        inflater.inflate(getLayoutRes(), container, false)
    } catch (ex: Exception) {
        ex.printStackTrace()
        throw ex
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleObservers.forEach {
            lifecycle.addObserver(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflateView(inflater, container, savedInstanceState)
    }

    protected fun navigateUp() {
        findNavController().navigateUp()
    }
}