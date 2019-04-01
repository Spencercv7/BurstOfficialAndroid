package betalab.ca.burstofficialandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private var pagerAdapter: BottomBarAdapter? = null

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.main_fragment,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()

        val mainAppTitle = activity?.findViewById<TextView>(R.id.main_app_title_text)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.group_item1 -> {
                    viewPager.currentItem = 0
                    mainAppTitle?.visibility = View.VISIBLE  //Burst app name visible on home
                }
                R.id.group_item2 -> {
                    viewPager.currentItem = 1
                    mainAppTitle?.visibility = View.GONE  //hide app name
                }
                else -> {
                    viewPager.currentItem = 2
                    mainAppTitle?.visibility = View.GONE //hide app name
                }
            }
            true
        }
    }

    private fun setupViewPager() {
        viewPager?.setPagingEnabled(false)
        viewPager?.offscreenPageLimit = 5
        pagerAdapter =
            BottomBarAdapter(activity!!.supportFragmentManager) //instantiate adapter
        //set the adapter for the pager to be the one with all the items
        viewPager?.adapter = pagerAdapter
    }


}