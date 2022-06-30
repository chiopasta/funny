package com.bitxflow.funny.biz.snack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_snack_long.*

class SnackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snack_long)

//        val pagerAdapter = ViewPagerAdapter(this)
//        snack_vp.adapter = pagerAdapter
//        indicator.setViewPager(snack_vp)

        snack_backpress_bt.setOnClickListener {
            finish()
        }
    }
}