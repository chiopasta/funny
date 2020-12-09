package com.bitxflow.funny

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bitxflow.funny.DB.GameDB
import com.bitxflow.funny.DB.GameDatabase
import com.bitxflow.funny.biz.coupon.CouponActivity
import com.bitxflow.funny.biz.fee.FeeActivity
import com.bitxflow.funny.biz.intro.IntroActivity
import com.bitxflow.funny.biz.login.LoginAcitivty
import com.bitxflow.funny.biz.product.ProductActivity
import com.bitxflow.funny.biz.recommend.RecommendActivity
import com.bitxflow.funny.biz.search.SearchActivity
import com.bitxflow.funny.send.SendServer
import kotlinx.android.synthetic.main.activity_coupon.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private val LOGIN_ACTIVITY : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nextIntent = Intent(this, LoginAcitivty::class.java)
        startActivityForResult(nextIntent,LOGIN_ACTIVITY)

        setContentView(R.layout.activity_main)
        hideSystemUI()
        search_bt.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        product_bt.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            startActivity(intent)
        }

        game_fee_bt.setOnClickListener {
            val intent = Intent(this, FeeActivity::class.java)
            startActivity(intent)
        }

        intro_bt.setOnClickListener {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }

        recommend_game_bt.setOnClickListener {
            val intent = Intent(this, RecommendActivity::class.java)
            startActivity(intent)
        }

        coupon.setOnClickListener {
            val intent = Intent(this, CouponActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                LOGIN_ACTIVITY -> {
                    val backPress = data?.getStringExtra("BackPress")
                    if (backPress.equals("BackPress")) finish()
                    else {
//                        backPress.toString()
                    }
                }
            }
        }
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext,"종료하실수 없습니다",Toast.LENGTH_SHORT).show()
//        super.onBackPressed()
    }


}