package com.example.mindfulmoments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        viewPager = findViewById(R.id.viewPager)
        bottomNavigation = findViewById(R.id.bottom_navigation)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        // Set up ViewPager adapter
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(LearnFragment(), "Learn")
        adapter.addFragment(UserFragment(), "User")
        viewPager.adapter = adapter

        // Connect ViewPager with BottomNavigationView
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                // Update Toolbar text based on the selected fragment
                when (position) {
                    0 -> {
                        toolbar.title = "Welcome!"
                        bottomNavigation.menu.findItem(R.id.navigation_home).isChecked = true
                    }
                    1 -> {
                        toolbar.title = "Learn Meditation"
                        bottomNavigation.menu.findItem(R.id.navigation_learn).isChecked = true
                    }
                    2 -> {
                        toolbar.title = "How's Your Day Going?"
                        bottomNavigation.menu.findItem(R.id.navigation_about).isChecked = true
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        // Set up BottomNavigationView
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> viewPager.currentItem = 0
                R.id.navigation_learn -> viewPager.currentItem = 1
                R.id.navigation_about -> viewPager.currentItem = 2
            }
            true
        }

        // Set Toolbar as the action bar
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Handle navigation item clicks
        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle navigation item clicks here
            when (menuItem.itemId) {
                R.id.nav_item1 -> {
                     startActivity(Intent(this, MeditateActivity::class.java))
                }
                R.id.nav_item2 -> {
                    startActivity(Intent(this, BreathActivity::class.java))
                }
            }
            // Close the navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}

