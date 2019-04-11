package betalab.ca.burstofficialandroid.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import betalab.ca.burstofficialandroid.ui.fragment.*

class NavigationAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MainFragment.newInstance()
            1 -> CampusMapFragment.newInstance()
            2 -> CatalogFragment.newInstance()
            3 -> ResourcesFragment.newInstance()
            else -> ProfileFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 5
    }
}