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
import com.bitxflow.funny.biz.login.LoginAcitivty
import com.bitxflow.funny.biz.product.ProductActivity
import com.bitxflow.funny.biz.search.SearchActivity
import com.bitxflow.funny.send.SendServer
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private var gameDatabase : GameDatabase? = null
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

//        testTask().execute()
    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext,"종료하실수 없습니다",Toast.LENGTH_SHORT).show()
//        super.onBackPressed()
    }

    internal inner class testTask : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String): String {
            val su = SendServer()

            val url = "games"
            return su.requestGET(url)

        }

        override fun onPostExecute(result: String) {
            if(result =="")
            {
                Toast.makeText(baseContext, "서버 통신오류, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }

            else {
                val `object` = JSONObject(result)
                val count = `object`.getInt("totalCount")
                val entities = `object`.getJSONArray("entities")
                if (count == 0) {

                }
                else
                {
                    gameDatabase = GameDatabase.getInstance(baseContext)
                    for(i in 0 until count)
                    {
                        val addRunnable = Runnable {
                            val json = entities.getJSONObject(i)
                            val gameID = json.getString("gameID")
                            val name = json.getString("name")
                            val engName = json.getString("engName")
                            val gameTime = json.getString("gameTime")
                            val expTime = json.getString("expTime")
                            val expText = json.getString("expText")
                            val expImg = json.getString("expImg")
                            val expUrl = json.getString("expUrl")
                            val type = json.getString("type")
                            val level = json.getString("level")
                            val people = json.getString("people")
                            val recommend = json.getString("recommend")
                            val hit = json.getInt("hit")
                            val memo = json.getString("memo")

                            val game = GameDB()
                            game.name = name
                            game.engName = engName
                            game.gameTime = gameTime
                            game.expTime = expTime
                            game.expText = expText
                            game.expUrl = expUrl
                            game.expImg = expImg
                            game.type = type
                            game.level = level
                            game.recommend = recommend
                            game.hit = hit
                            game.memo = memo
                            game.people = people

                            gameDatabase?.gameDao()?.insert(game)
                        }
                        val addThread = Thread(addRunnable)
                        addThread.start()
                    }
                }
            }

        }
    }

}