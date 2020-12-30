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
        val name = intent.extras!!.getString("name").toString()
        val memo = intent.extras!!.getString("memo").toString()

        val pagerAdapter = RulePagerAdapter(this,name,memo)
        viewpager.adapter = pagerAdapter
        indicator.setViewPager(viewpager)

        rule_backpress_bt.setOnClickListener {
            finish()
        }

    }
}