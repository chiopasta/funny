package com.bitxflow.funny.biz.coupon

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bitxflow.funny.R
import com.bitxflow.funny.send.SendServer
import kotlinx.android.synthetic.main.activity_coupon.*
import kotlinx.android.synthetic.main.activity_coupon.coupon_back_bt
import kotlinx.android.synthetic.main.activity_coupon.coupon_code
import kotlinx.android.synthetic.main.activity_coupon.coupon_tx2
import kotlinx.android.synthetic.main.activity_coupon.make_coupon_bt
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sns_coupon.*
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CouponActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)
        coupon_pbar.visibility = View.GONE

        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE,1)
        val df : DateFormat = SimpleDateFormat("yyyy년MM월dd일")
        val tomorrow =  df.format(cal.time)
        cal.add(Calendar.MONTH,3)
        val exp_date =  df.format(cal.time)

        coupon_tx2.text = "사용기한 : "+ tomorrow + " ~"

        make_coupon_bt.setOnClickListener {
            coupon_pbar.visibility = View.VISIBLE
            SendTask().execute()
        }

        coupon_back_bt.setOnClickListener {
            finish()
        }
    }

    internal inner class SendTask : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String): String {
            val su = SendServer()

            val url = "coupons"
            val postDataParams = JSONObject()
            return su.requestPOST(url,postDataParams)

        }

        override fun onPostExecute(result: String) {
            make_coupon_bt.isClickable = false
            coupon_pbar.visibility = View.GONE

            if(result =="")
            {
                Toast.makeText(baseContext, "서버 통신오류, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
            else {

//                make_coupon_bt.text = "20%"
//                make_coupon_bt.textSize = 35.0f
//                coupon_code.text = "- " + "20201206_00001"
                val json = JSONObject(result)
                val success = json.getBoolean("success")
                if(success)
                {
                    val code = json.getString("name")
                    val content = json.getString("content")
                    coupon_code.text = "- " + code
                    make_coupon_bt.text = content
                    make_coupon_bt.textSize = 30.0f
                    make_coupon_bt.isClickable = false
                }
                else{
                    coupon_code.text = "- 다시 시도해주세요"
                    make_coupon_bt.isClickable = true
                }

            }

        }
    }
}