package com.bitxflow.funny.biz.coupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_sns_coupon.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SnsCouponActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sns_coupon)

//        val cal = Calendar.getInstance()
//        cal.add(Calendar.DATE,1)
//        val df : DateFormat = SimpleDateFormat("yyyy년MM월dd일")
//        val tomorrow =  df.format(cal.time)
//        cal.add(Calendar.MONTH,3)
//        val exp_date =  df.format(cal.time)
//
//        coupon_tx2.text = "사용기한 : "+ tomorrow + " ~ " + exp_date

        coupon_back_bt.setOnClickListener {
            finish()
        }
    }
}