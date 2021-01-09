package com.bitxflow.funny.biz.coupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        select_backpress_bt.setOnClickListener {
            finish()
        }

        req_coupon_bt.setOnClickListener {
            val intent = Intent(this, CouponActivity::class.java)
            startActivity(intent)
        }

        sns_coupon_bt.setOnClickListener {
            val intent = Intent(this, SnsCouponActivity::class.java)
            startActivity(intent)
        }
    }
}