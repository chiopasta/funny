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

        gameDatabase = GameDatabase.getInstance(baseContext)

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

    override fun onBackPressed() {
        Toast.makeText(applicationContext,"종료하실수 없습니다",Toast.LENGTH_SHORT).show()
//        super.onBackPressed()
    }

    internal inner class reLoadGamesTask : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String): String {
            val su = SendServer()

            val url = "games"
            return su.requestGET(url)

        }

        override fun onPostExecute(result: String) {
            if (result == "") {
                Toast.makeText(baseContext, "서버 통신오류, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
//                Toast.makeText(baseContext, "디비 삽입", Toast.LENGTH_SHORT).show()

//                val addRunnable = Runnable {
//
//                    gameDatabase?.gameDao()?.deleteAll()
//                    val games: List<GameDB>? = gameDatabase?.gameDao()?.getGames()
//
//                    if (games!!.isNotEmpty()) {
//
//                        Log.d("bitx_log", "DB test in")
//
//                        val game = GameDB()
//                        game.name = "할리갈리"
//                        val str = "순발력,퍼즐"
//                        game.type = str
//                        game.expUrl =
//                            "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxOTEyMDNfMjA4%2FMDAxNTc1MzUyNDU0NjU2.5baBn7OpRGonQIUUlrpjVOusF4g4UAek4j_PqhZ2JGcg.Wvnz79SMBX51Hhq_eiR_6hvlDT1zCQpP2SUj29cjP2Mg.JPEG.qnsegtgk%2F%2525B7%2525E7%2525B9%2525CC%2525C5%2525A5%2525BA%2525EA%252B%2525C6%2525AE%2525B7%2525A1%2525BA%2525ED.jpg&type=sc960_832"
//                        game.people = "2,3,4,5,6"
//                        game.level = "쉬움";
//
//                        val game2 = GameDB()
//                        game2.name = "블러프"
//                        val str2 = "추리,구라,순발력"
//                        game2.type = str2
//
//                        val numbers2 = intArrayOf(3, 4, 5, 6)
//                        game2.people = "3,4,5,6"
//
//                        game2.level = "중간";
//
//
//                        val game3 = GameDB()
//                        game3.name = "뱅"
//                        val str3 = "추리,구라"
//                        game3.type = str3
//
//                        game3.people = "6,7,8,9"
//                        game3.level = "어려움";
//
//                        val game4 = GameDB()
//                        game4.name = "루미큐브"
//                        val str4 = "추리,구라,퍼즐,심리"
//                        game4.level = "중간";
//                        game4.type = str4
//
//                        val numbers4 = intArrayOf(2, 3, 4, 5)
//                        game4.people = "2,3,4,5"
//                    }
//                }
//
//                val addThread = Thread(addRunnable)
//                addThread.start()
            }
            else {
                val `object` = JSONObject(result)
                val count = `object`.getInt("totalCount")
                val entities = `object`.getJSONArray("entities")
                if (count == 0) {

                }
                else
                {

                    val deleteRunnable = Runnable {
                        gameDatabase?.gameDao()?.deleteAll()
                    }
                    val deleteThread = Thread(deleteRunnable)
                    deleteThread.start()

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