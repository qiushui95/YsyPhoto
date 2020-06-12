package son.ysy.photo.ui.mine

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_home_mine.*
import me.yangcx.base.annotations.BindLayoutRes
import me.yangcx.base.fragments.BaseFragment
import son.ysy.photo.R
import son.ysy.photo.ui.items.itemNotLoginView

@BindLayoutRes(R.layout.fragment_home_mine)
class HomeMineFragment : BaseFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvHomeMine.withModels {
            itemNotLoginView {
                id(javaClass.name)
            }
        }
    }
}