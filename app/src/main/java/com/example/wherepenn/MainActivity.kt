package com.example.wherepenn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wherepenn.fragments.BuildingFragment
import com.example.wherepenn.fragments.FoodTruckFragment
import com.example.wherepenn.utils.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // ViewPagerAdapter.kt in .utils
    private val adapter = ViewPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // create tabs in TabLayout
        createTabs()
    }

    private fun createTabs() {
        // Create Tabs with headings
        adapter.addFragment(FoodTruckFragment(), "FoodTruck")
        adapter.addFragment(BuildingFragment(), "Buildings")

        // Link fragments with the viewPager
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    // Terminate App when backPressed
    override fun onBackPressed() {
        finish()
    }
}
