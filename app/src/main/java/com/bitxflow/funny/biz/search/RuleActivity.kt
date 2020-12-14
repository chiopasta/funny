package com.bitxflow.funny.biz.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_rule.*

class RuleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rule)

        val intent = intent
        val engName = intent.extras!!.getString("engName").toString()
        val memo = intent.extras!!.getString("memo").toString()

        val pagerAdapter = RulePagerAdapter(this,engName,memo)
        viewpager.adapter = pagerAdapter
        indicator.setViewPager(viewpager)

    }
}