package betalab.ca.burstofficialandroid.activity


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import betalab.ca.burstofficialandroid.BottomBarAdapter
import betalab.ca.burstofficialandroid.R
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.navigation.NavigationView


class MainActivity : FragmentActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var pagerAdapter: BottomBarAdapter? = null
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(betalab.ca.burstofficialandroid.R.layout.activity_main)

        setUpNavigationDrawer()

        setupViewPager()

        val mainTitleText = findViewById<TextView>(R.id.main_title_text)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.group_item1 -> {
                    viewPager.currentItem = 0
                    mainTitleText.visibility = View.VISIBLE //show text Burst only on home screen
                }
                R.id.group_item2 -> {
                    viewPager.currentItem = 1
                    mainTitleText.visibility = View.GONE
                }
                R.id.group_item3 -> {
                    viewPager.currentItem = 2
                }
                R.id.group_item4 -> {
                    viewPager.currentItem = 3
                }
                else -> {
                    viewPager.currentItem = 2
                    mainTitleText.visibility = View.GONE
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


    private fun setUpNavigationDrawer() {
        drawer = findViewById(betalab.ca.burstofficialandroid.R.id.main_drawer_layout)
        val toolbar = findViewById<Toolbar>(betalab.ca.burstofficialandroid.R.id.main_toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            betalab.ca.burstofficialandroid.R.string.navigation_drawer_open,
            betalab.ca.burstofficialandroid.R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle) //create toggle icon that comes with animation
        toggle.syncState()

        navigationView = findViewById(R.id.drawer_view)
        navigationView.setNavigationItemSelectedListener(this)
        val drawerCloseButton = navigationView.getHeaderView(0).findViewById<ImageView>(R.id.drawer_menu_close_button)
        drawerCloseButton.setOnClickListener { drawer.closeDrawer(GravityCompat.START) } //button embedded in drawer that closes drawer
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.drawer_campus_map_item -> //toasts in place for indication of item clicked
                Toast.makeText(this, "Campus Map", Toast.LENGTH_LONG).show()
            R.id.drawer_catalog_item ->
                Toast.makeText(this, "Catalog", Toast.LENGTH_LONG).show()
            else ->
                Toast.makeText(this, "Resources", Toast.LENGTH_LONG).show()
        }
        drawer.closeDrawer(GravityCompat.START)  //close drawer once item selected
        return true  //keeps item selected after menu closes
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {  //if back pressed and nav drawer open
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


}