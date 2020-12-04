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

        new_coupon_bt.setOnClickListener{
            //쿠폰발급 끝
        }

    }

    internal inner class SendTask : AsyncTask<String, String, String>() {
        var userId : String =""
        var userPassword : String = ""

        override fun doInBackground(vararg params: String): String {
            val su = SendServer()
            userId = params[0].trim()
            userPassword = params[1].trim()

            val url = "coupons"
            val postDataParams = JSONObject()
            postDataParams.put("userid", userId.toUpperCase())
            postDataParams.put("password", userPassword)

            return su.requestPOST(url,postDataParams)

        }

        override fun onPostExecute(result: String) {
            login_bt.isClickable = true
            login_pbar.visibility = View.GONE

            if(result =="")
            {
                Toast.makeText(baseContext, "서버 통신오류, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                val json = JSONObject(result)
                val success = json.getBoolean("success")
                setResult(Activity.RESULT_OK, Intent().putExtra("BackPress", userId))
                finish()
            }
        }
    }
}