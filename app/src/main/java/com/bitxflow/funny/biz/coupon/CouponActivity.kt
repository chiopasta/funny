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
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class CouponActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)
        coupon_pbar.visibility = View.GONE

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
//            postDataParams.put("userid", userId.toUpperCase())
//            postDataParams.put("password", userPassword)

            return su.requestPOST(url,postDataParams)

        }

        override fun onPostExecute(result: String) {
            make_coupon_bt.isClickable = false
            coupon_pbar.visibility = View.GONE

//            if(result =="")
//            {
//                Toast.makeText(baseContext, "서버 통신오류, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
//            }
//            else {
                make_coupon_bt.text = "20%"
                make_coupon_bt.textSize = 35.0f
//                val json = JSONObject(result)
//                val success = json.getBoolean("success")

//            }
        }
    }
}