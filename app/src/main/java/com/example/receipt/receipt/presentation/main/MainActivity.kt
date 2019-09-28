package com.example.receipt.receipt.presentation.main


import android.os.Bundle
import android.view.MenuItem
import com.example.receipt.receipt.presentation.common.RxActivity
import com.example.receipt.receipt.presentation.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.example.receipt.receipt.R
import com.example.receipt.receipt.presentation.scan.ScanFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : RxActivity(), NavigationView.OnNavigationItemSelectedListener {


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, HomeFragment())
                    .commit()
                setTitleToolbarDesign("ホーム")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_camera -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, ScanFragment())
                    .commit()
                setTitleToolbarDesign("カメラ")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitleToolbarDesign("ホーム")

        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, HomeFragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, HomeFragment())
            .commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun setTitleToolbarDesign(title: String?) {
        toolbar.setTitle(title ?: "")
    }

}
