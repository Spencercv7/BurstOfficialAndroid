package betalab.ca.burstofficialandroid.ui.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentActivity
import betalab.ca.burstofficialandroid.*
import betalab.ca.burstofficialandroid.ui.adapter.NavigationAdapter
import betalab.ca.burstofficialandroid.ui.util.NotificationUtil
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : FragmentActivity(), NavigationView.OnNavigationItemSelectedListener {
        private var viewPagerAdapter: NavigationAdapter? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            setUpNavigationDrawer()
            setUpViewPager()
            setFragment(1) //open main fragment

            main_profile_pic.setOnClickListener {
                setFragment(5) //open profile fragment
                uncheckDrawerItems()
                }
            close_profile_pic.setOnClickListener {
                setFragment(1) //close profile fragment and go to main fragment
            }
            main_profile_pic.setOnClickListener {
                val intent = Intent(this, ProfileActivity::class.java)//should be new activity just temp
                startActivity(intent)
            }
            list_yours_button.setOnClickListener {
                val intent = Intent(this, NewEventActivity::class.java)//should be new activity just temp
                startActivity(intent)
            }
        }


    private fun setUpViewPager(){
        mainViewPager.setPagingEnabled(false)
        mainViewPager.offscreenPageLimit = 5
        viewPagerAdapter = NavigationAdapter(this.supportFragmentManager)//instantiate adapter
        //set the adapter for the pager to be the one with all the items
        mainViewPager?.adapter = viewPagerAdapter

    }


    // 1 -> Main, 2 -> CampusMap, 3 -> Catalog, 4 -> Resources, 5 -> Profile
    private fun setFragment(fragmentInt: Int){
        when (fragmentInt){  //set correct fragment in the container
            1 -> { mainViewPager.setCurrentItem(0, false)
                   drawer_view.menu.getItem(0).isChecked = true //highlight home button on drawer menu
                   nav_title_text.text = ""}
            2 -> { mainViewPager.setCurrentItem(1, false)
                   nav_title_text.text = getString(R.string.campus_map)}
            3 -> { mainViewPager.setCurrentItem(2, false)
                   nav_title_text.text = getString(R.string.catalog)}
            4 -> { mainViewPager.setCurrentItem(3, false)
                   nav_title_text.text = getString(R.string.resources)}
            else -> { mainViewPager.setCurrentItem(4, false)
                      nav_title_text.text = ""}
        }

        if (fragmentInt != 1) //Set app title visible or not
            main_app_title_text.visibility = View.GONE
        else main_app_title_text.visibility = View.VISIBLE

        //set visibility of profile pic in top right or X to close profile fragment
        if (fragmentInt == 5){
            close_profile_pic.visibility = View.VISIBLE
            main_profile_pic.visibility = View.GONE
        }
        else {
            close_profile_pic.visibility = View.GONE
            main_profile_pic.visibility = View.VISIBLE
        }
    }

    private fun setUpNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(this, main_drawer_layout, main_toolbar, betalab.ca.burstofficialandroid.R.string.navigation_drawer_open, betalab.ca.burstofficialandroid.R.string.navigation_drawer_close)
        main_drawer_layout.addDrawerListener(toggle) //create toggle icon that comes with animation
        toggle.syncState()

        drawer_view.setNavigationItemSelectedListener(this)
        val drawerCloseButton = drawer_view.getHeaderView(0).findViewById<ImageView>(R.id.drawer_menu_close_button)
        drawerCloseButton.setOnClickListener { main_drawer_layout.closeDrawer(GravityCompat.START) } //button embedded in drawer that closes drawer
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.drawer_home_item ->  //home added to drawer menu bc no way to get there otherwise
                setFragment(1) //main fragment
            R.id.drawer_campus_map_item ->
                setFragment(2) //campus map fragment
            R.id.drawer_catalog_item ->
                setFragment(3) //catalog fragment
            else ->
                setFragment(4) //resources fragment
        }
        main_drawer_layout.closeDrawer(GravityCompat.START)  //close drawer once item selected
        return true  //keeps item selected after menu closes
    }

    //uncheck all drawer menu items
    private fun uncheckDrawerItems() {
        val menu = drawer_view.menu
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            item.isChecked = false
        }
    }


    override fun onBackPressed() {
        when {
            main_drawer_layout.isDrawerOpen(GravityCompat.START) -> //if back pressed and nav drawer open
                main_drawer_layout.closeDrawer(GravityCompat.START)
            close_profile_pic.visibility == View.VISIBLE -> setFragment(1)  //if profile page open go to home
            else -> super.onBackPressed()
        }
    }

}