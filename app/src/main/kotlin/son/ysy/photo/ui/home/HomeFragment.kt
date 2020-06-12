package son.ysy.photo.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import kotlinx.android.synthetic.main.fragment_home.*
import me.yangcx.base.annotations.BindLayoutRes
import me.yangcx.base.fragments.BaseFragment
import son.ysy.photo.R

@BindLayoutRes(R.layout.fragment_home)
class HomeFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bnbHome
            .setActiveColor(R.color.colorHomeBottomNavigationActive)
            .setInActiveColor(R.color.colorHomeBottomNavigationInactive)
            .setBarBackgroundColor(R.color.color00000000)
            .addItem(
                BottomNavigationItem(
                    R.drawable.ic_home_index_checked,
                    R.string.string_home_menu_index
                ).setInactiveIconResource(R.drawable.ic_home_index_normal)
            ).addItem(
                BottomNavigationItem(
                    R.drawable.ic_home_record_checked,
                    R.string.string_home_menu_record
                ).setInactiveIconResource(R.drawable.ic_home_record_normal)
            ).addItem(
                BottomNavigationItem(
                    R.drawable.ic_home_mine_checked,
                    R.string.string_home_menu_mine
                ).setInactiveIconResource(R.drawable.ic_home_mine_normal)
            ).initialise()
        vpHome.adapter = HomeAdapter(this)
        (vpHome[0] as? RecyclerView)?.apply {
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bnbHome.selectTab(position, false)
            }
        })
        bnbHome.setTabSelectedListener(object : BottomNavigationBar.SimpleOnTabSelectedListener() {
            override fun onTabSelected(position: Int) {
                vpHome.setCurrentItem(position, true)
            }
        })
    }
}