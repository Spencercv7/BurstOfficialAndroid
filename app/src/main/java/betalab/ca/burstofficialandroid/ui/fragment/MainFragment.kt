package betalab.ca.burstofficialandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import betalab.ca.burstofficialandroid.R
import betalab.ca.burstofficialandroid.ui.adapter.BottomBarAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private var pagerAdapter: BottomBarAdapter? = null

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.fragment_main,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.group_item1 -> {
                    mainFragViewPager.currentItem = 0
                }
                R.id.group_item2 -> {
                    mainFragViewPager.currentItem = 1
                }
                R.id.group_item3 -> {
                    mainFragViewPager.currentItem = 2
                }
                else -> {
                    mainFragViewPager.currentItem = 3
                }
            }
            true
        }
    }

    private fun setupViewPager() {
        mainFragViewPager?.setPagingEnabled(false)
        mainFragViewPager?.offscreenPageLimit = 6
        pagerAdapter =
            BottomBarAdapter(activity!!.supportFragmentManager) //instantiate adapter
        //set the adapter for the pager to be the one with all the items
        mainFragViewPager?.adapter = pagerAdapter
    }


}