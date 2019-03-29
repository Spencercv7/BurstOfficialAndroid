package betalab.ca.burstofficialandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class BottomBarAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> ExploreFragment.newInstance()
            2 -> FloatingButton.newInstance()
            else -> ExploreFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 4
    }
}