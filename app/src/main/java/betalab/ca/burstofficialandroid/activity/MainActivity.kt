package betalab.ca.burstofficialandroid.activity


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import betalab.ca.burstofficialandroid.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : FragmentActivity(), NavigationView.OnNavigationItemSelectedListener {
        private lateinit var drawer: DrawerLayout
        private lateinit var navigationView: NavigationView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            setUpNavigationDrawer()
            setFragment(1) //open main fragment
            //setupViewPager()

            main_profile_pic.setOnClickListener {
                setFragment(5) //open profile fragment
                uncheckDrawerItems()
                }
            close_profile_pic.setOnClickListener {
                setFragment(1) //close profile fragment and go to main fragment
            }
        }

    // 1 -> Main, 2 -> CampusMap, 3 -> Catalog, 4 -> Resources, 5 -> Profile
    private fun setFragment(fragmentInt: Int){
        val ft = supportFragmentManager.beginTransaction()
        when (fragmentInt){  //set correct fragment in the container
            1 -> { ft.replace(R.id.main_fragment_container, MainFragment())
                   navigationView.menu.getItem(0).isChecked = true //highlight home button on drawer menu
                   nav_title_text.text = ""}
            2 -> { ft.replace(R.id.main_fragment_container, CampusMapFragment())
                   nav_title_text.text = getString(R.string.campus_map)}
            3 -> { ft.replace(R.id.main_fragment_container, CatalogFragment())
                   nav_title_text.text = getString(R.string.catalog)}
            4 -> { ft.replace(R.id.main_fragment_container, ResourcesFragment())
                   nav_title_text.text = getString(R.string.resources)}
            else -> { ft.replace(R.id.main_fragment_container, ProfileFragment())
                      nav_title_text.text = ""}
        }
        ft.commit()

        if (fragmentInt == 1) //Set app title visible or not
            main_app_title_text.visibility = View.VISIBLE
        else main_app_title_text.visibility = View.GONE

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
        drawer = findViewById(R.id.main_drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, main_toolbar, betalab.ca.burstofficialandroid.R.string.navigation_drawer_open, betalab.ca.burstofficialandroid.R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle) //create toggle icon that comes with animation
        toggle.syncState()

        navigationView = findViewById(R.id.drawer_view)
        navigationView.setNavigationItemSelectedListener(this)
        val drawerCloseButton = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.drawer_menu_close_button)
        drawerCloseButton.setOnClickListener { drawer.closeDrawer(GravityCompat.START) } //button embedded in drawer that closes drawer
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
        drawer.closeDrawer(GravityCompat.START)  //close drawer once item selected
        return true  //keeps item selected after menu closes
    }

    //uncheck all drawer menu items
    private fun uncheckDrawerItems() {
        val menu = navigationView.menu
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            item.isChecked = false
        }
    }


    override fun onBackPressed() {
        when {
            drawer.isDrawerOpen(GravityCompat.START) -> //if back pressed and nav drawer open
                drawer.closeDrawer(GravityCompat.START)
            close_profile_pic.visibility == View.VISIBLE -> setFragment(1)  //if profile page open go to home
            else -> super.onBackPressed()
        }
    }

}