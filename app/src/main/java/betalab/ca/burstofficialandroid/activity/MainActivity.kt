package betalab.ca.burstofficialandroid.activity


import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import betalab.ca.burstofficialandroid.BottomBarAdapter
import betalab.ca.burstofficialandroid.NoSwipePager
import betalab.ca.burstofficialandroid.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {
        private var pagerAdapter: BottomBarAdapter? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            setupViewPager()
            bottom_navigation.setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.group_item1 -> {
                        viewPager.currentItem = 0
                    }
                    R.id.group_item2 -> {
                        viewPager.currentItem = 1
                    }
                    R.id.group_item3 -> {
                        viewPager.currentItem = 2
                    }
                    R.id.group_item4 -> {
                        viewPager.currentItem = 3
                    }

                }
                true
            }
        }

        private fun setupViewPager() {
            viewPager?.setPagingEnabled(false)
            viewPager?.offscreenPageLimit = 5
            pagerAdapter = BottomBarAdapter(supportFragmentManager) //instantiate adapter
            //set the adapter for the pager to be the one with all the items
            viewPager?.adapter = pagerAdapter
        }

}