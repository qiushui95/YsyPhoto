package son.ysy.photo.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import son.ysy.photo.exceptions.UnsupportPositionException
import son.ysy.photo.ui.index.HomeIndexFragment
import son.ysy.photo.ui.mine.HomeMineFragment
import son.ysy.photo.ui.record.HomeRecordFragment

class HomeAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment.childFragmentManager, fragment.viewLifecycleOwner.lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int) = when (position) {
        0 -> HomeIndexFragment()
        1 -> HomeRecordFragment()
        2 -> HomeMineFragment()
        else -> throw UnsupportPositionException(position)
    }
}