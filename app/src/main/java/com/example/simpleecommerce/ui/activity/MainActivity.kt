package com.example.simpleecommerce.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.example.simpleecommerce.R
import com.example.simpleecommerce.ui.fragment.HomeStuffFragment
import com.example.simpleecommerce.ui.fragment.NewsFragment
import com.example.simpleecommerce.ui.fragment.PurchaseHistoryFragment
import com.example.simpleecommerce.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val homeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView(savedInstanceState)
    }

    private fun initView(bundle: Bundle?) {
        bottom_nav_main_menu.setOnNavigationItemSelectedListener(this)

        if (bundle == null){
            goToFragment(HomeStuffFragment())
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        return when (p0.itemId) {
            R.id.home_menu -> {
                goToFragment(HomeStuffFragment())
                true
            }
            R.id.news_menu -> {
                goToFragment(NewsFragment())
                true
            }
            R.id.history_menu -> {
                goToFragment(PurchaseHistoryFragment())
                true
            }
            else -> {
                false
            }
        }
    }

    private fun goToFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_menu_transaction, fragment, null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        homeViewModel.dispatchAll()
    }
}
