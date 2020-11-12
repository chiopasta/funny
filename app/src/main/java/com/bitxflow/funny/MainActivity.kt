package com.bitxflow.funny

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bitxflow.funny.biz.login.LoginAcitivty
import com.bitxflow.funny.biz.product.ProductActivity
import com.bitxflow.funny.biz.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val LOGIN_ACTIVITY : Int = 0
    private val TAG = "bitx_log"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.packageManager.setComponentEnabledSetting(
            ComponentName(this, MainActivity::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

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

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        Log.d("bitx_log", "Focus changed !")

        if (!hasFocus) {
            Log.d("bitx_log", "Lost focus !")
            val closeDialog = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
            sendBroadcast(closeDialog)
            hideSystemUI()
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


    override fun onUserLeaveHint() {
//        super.onUserLeaveHint()
        Log.d("bitx_log","home button?")
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val X = event.x.toInt()
        val Y = event.y.toInt()
        val eventaction = event.action
        if (Y < 400) {
            onWindowFocusChanged(true)
        }
        return true
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            Log.i("Home Button", "Clicked")
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
        }
        return false
    }


    fun showToast(msg:String) {
        Toast.makeText(this@MainActivity,msg, Toast.LENGTH_SHORT).show()
    }
}