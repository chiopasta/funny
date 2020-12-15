package com.bitxflow.funny.biz.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_intro.indicator

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val pagerAdapter = ViewPagerAdapter(this)
        intro_viewpager.adapter = pagerAdapter
        indicator.setViewPager(intro_viewpager)

        intro_backpress_bt.setOnClickListener {
            finish()
        }
    }

}
