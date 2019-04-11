package betalab.ca.burstofficialandroid.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import betalab.ca.burstofficialandroid.ui.fragment.ExploreFragment
import betalab.ca.burstofficialandroid.ui.fragment.CalendarFragment
import betalab.ca.burstofficialandroid.ui.fragment.HomeFragment
import betalab.ca.burstofficialandroid.ui.fragment.NotificationFragment

class BottomBarAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> ExploreFragment.newInstance()
            2 -> CalendarFragment.newInstance()
            else -> NotificationFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 4
    }
}