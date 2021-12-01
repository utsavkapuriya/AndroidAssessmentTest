package com.test.androidassesmenttest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.androidassesmenttest.R
import com.test.androidassesmenttest.model.Result
import com.test.androidassesmenttest.util.putArgs

class LocationListActivity : AppCompatActivity(), LocationListAdapter.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState?.let {
            supportFragmentManager.executePendingTransactions()
            val fragmentById = supportFragmentManager.findFragmentById(R.id.fragment_container)
            fragmentById?.let {
                supportFragmentManager.beginTransaction()
                    .remove(fragmentById).commit()
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LocationListFragment()).commit()
    }

    override fun onLocationItemSelected(data: Result) {
        val detailFragment = DetailFragment.newInstance(data)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, detailFragment)
            addToBackStack(null)
            commit()
        }
    }
}