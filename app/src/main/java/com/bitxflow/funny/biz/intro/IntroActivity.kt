package com.bitxflow.funny.biz.intro

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_intro.view.*

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val pagerAdapter = ViewPagerAdapter(this)
        intro_viewpager.adapter = pagerAdapter
        indicator.setViewPager(intro_viewpager)
    }

}
