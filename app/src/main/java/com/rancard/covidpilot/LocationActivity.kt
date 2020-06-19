package com.rancard.covidpilot

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.rancard.covidpilot.ui.LocationUpdateFragment
import com.rancard.covidpilot.ui.PermissionRequestFragment
import com.rancard.covidpilot.ui.PermissionRequestType
import com.rancard.covidpilot.ui.main.SectionsPagerAdapter
import com.rancard.covidpilot.databinding.ActivityLocationBinding

class LocationActivity : AppCompatActivity(), PermissionRequestFragment.Callbacks,
    LocationUpdateFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_location)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {

            val fragment = LocationUpdateFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }


//        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
//        val viewPager: ViewPager = findViewById(R.id.view_pager)
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = findViewById(R.id.tabs)
//        tabs.setupWithViewPager(viewPager)
//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    override fun displayLocationUI() {
        val fragment = LocationUpdateFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun requestFineLocationPermission() {
        val fragment = PermissionRequestFragment.newInstance(PermissionRequestType.FINE_LOCATION)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun requestBackgroundLocationPermission() {
        val fragment = PermissionRequestFragment.newInstance(
            PermissionRequestType.BACKGROUND_LOCATION
        )

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}