package com.bitxflow.funny

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bitxflow.funny.biz.beginner.BeginnerActivity
import com.bitxflow.funny.biz.coupon.SelectActivity
import com.bitxflow.funny.biz.fee.FeeActivity
import com.bitxflow.funny.biz.intro.IntroActivity
import com.bitxflow.funny.biz.login.LoginAcitivty
import com.bitxflow.funny.biz.saveImg.ImgLinkActivity
import com.bitxflow.funny.biz.recommend.RecommendActivity
import com.bitxflow.funny.biz.search.SearchActivity
import com.bitxflow.funny.biz.snack.SnackActivity
import kotlinx.android.synthetic.main.activity_main.*


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
//            val intent = Intent(this, ProductActivity::class.java)
            val intent = Intent(this, SnackActivity::class.java)
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
            val intent = Intent(this, SelectActivity::class.java)
            startActivity(intent)
        }

        question_bt.setOnClickListener{
            Toast.makeText(baseContext,"준비중 입니다",Toast.LENGTH_SHORT).show()
//            kr.stiel.onenight
//            val Intent = packageManager.getLaunchIntentForPackage("kr.stiel.onenight")
//            if(intent !=null)
//                startActivity(Intent)
        }

        beginner.setOnClickListener {
            val intent = Intent(this, BeginnerActivity::class.java)
            startActivity(intent)
        }

        if(checkPersmission()){

        }
        else{
            requestPermission()
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
//        Toast.makeText(applicationContext,"종료하실수 없습니다",Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE, CAMERA),
            1)
    }

    // 카메라 권한 체크
    private fun checkPersmission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    // 권한요청 결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Log.d("TAG", "Permission: " + permissions[0] + "was " + grantResults[0] + "카메라 허가 받음 예이^^")
//        }else{
//            Log.d("TAG","카메라 허가 못받음 ㅠ 젠장!!")
//        }
    }

}