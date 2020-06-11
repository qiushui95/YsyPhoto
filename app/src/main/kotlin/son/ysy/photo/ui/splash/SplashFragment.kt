package son.ysy.photo.ui.splash

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import me.yangcx.base.annotations.BindLayoutRes
import me.yangcx.base.fragments.BaseFragment
import son.ysy.photo.R

@BindLayoutRes(R.layout.fragment_splash)
class SplashFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            delay(1500)
            findNavController().navigate(SplashFragmentDirections.toHome())
        }
    }
}