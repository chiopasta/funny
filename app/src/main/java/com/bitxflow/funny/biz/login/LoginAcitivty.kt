package com.bitxflow.funny.biz.login

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.print.PrintAttributes
import android.print.PrintJob
import android.print.PrintManager
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bitxflow.funny.DB.GameDB
import com.bitxflow.funny.DB.GameDatabase
import com.bitxflow.funny.DB.User
import com.bitxflow.funny.R
import com.bitxflow.funny.send.SendServer
import com.bitxflow.funny.util.PdfDocumentAdapter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.hidden_dialog.*
import org.json.JSONObject
import java.io.File

class LoginAcitivty : AppCompatActivity() {

    private var gameDatabase : GameDatabase? = null
    private var count = 0

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkPermission()
        gameDatabase = GameDatabase.getInstance(baseContext)

        login_bt.setOnClickListener{
            login_pbar.visibility = View.VISIBLE
            login_bt.isClickable = false
            var id = ""
            var pw = ""
            id = username.text.toString()
            pw = password.text.toString()

            if(id.isNullOrBlank()) {
                var hasDB = false

                val getUserRunable = Runnable {
                    val users = gameDatabase?.userDao()?.getUsers()

                    if (id.isNullOrEmpty()) {
                        if (users!!.isEmpty()) {
                            hasDB = true
                            NoIDTask().execute()

                        } else {
                            val user = users!!.last()
                            id = user.userID.toString()
                            pw = user.pass.toString()
                            Log.d("bitx_log","DB  id $id ... pw : $pw")
                            SendTask().execute(id, pw)
                        }
                    }

                }

                val thread = Thread(getUserRunable)
                thread.start()

            }
            else {
                if(pw.isNullOrBlank()) {
                    Toast.makeText(applicationContext, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    login_pbar.visibility = View.INVISIBLE
                    login_bt.isClickable = true
                }
                else
                    SendTask().execute(id, pw)
            }
        }

        reload_bt.setOnClickListener {

            val deleteRunnable = Runnable {
                gameDatabase?.gameDao()?.deleteAll()
            }
            val deleteThread = Thread(deleteRunnable)
            deleteThread.start()

            reloadGamesTask().execute()

        }

        hidden_tx.setOnClickListener {
            count++
            if(count>10)
            {
                val builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.hidden_dialog,null)
                val hideenText = dialogView.findViewById<EditText>(R.id.hidden_et)

                builder.setView(dialogView)
                    .setNegativeButton("확인"){ dialogInterface, i ->
                        val hidden_src = hideenText.text.toString()
                        if(hidden_src=="i112233*")
                        {
                            finish()
                        }
                    }
                builder.show()
            }
        }


    }

    internal inner class SendTask : AsyncTask<String, String, String>() {
        var userId : String = ""
        var userPassword : String = ""

        override fun doInBackground(vararg params: String): String {
            val su = SendServer()
            userId = params[0].trim()
            userPassword = params[1].trim()

            val url = "login"
            val postDataParams = JSONObject()

            Log.d("bitx_log","send ID : $userId")

//            userId = "funny"
//            userPassword = "funny2020!"

            postDataParams.put("userid", userId)
            postDataParams.put("pass", userPassword)


            return su.requestPOST(url,postDataParams)

        }

        override fun onPostExecute(result: String) {
            login_bt.isClickable = true
            login_pbar.visibility = View.GONE

            Log.d("bitx_log","result: $result")
            if(result =="")
            {
                Toast.makeText(baseContext, "서버 통신오류, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                val json = JSONObject(result)
                val success = json.getBoolean("success")
//                setResult(Activity.RESULT_OK, Intent().putExtra("BackPress", userId))
//                finish()
                if(success)
                {
                    val addRunnable = Runnable {
                        val users : List<User>? =  gameDatabase?.userDao()?.getUsers()
                        if(users!!.isNotEmpty()) {
                            gameDatabase?.userDao()?.deleteAll()
                        }
                        val user = User()
                        user.userID = userId
                        user.pass = userPassword
                        gameDatabase?.userDao()?.insert(user)
                    }
                    val addThread = Thread(addRunnable)
                    addThread.start()
                    setResult(Activity.RESULT_OK, Intent().putExtra("BackPress", userId))
                    finish()
                }
                else {
                    Toast.makeText(baseContext, json.getString("message"), Toast.LENGTH_SHORT).show()
                    val addRunnable = Runnable {
                        gameDatabase?.userDao()?.deleteAll()
                    }
                    val addThread = Thread(addRunnable)
                    addThread.start()
                }
            }
        }
    }

    internal inner class NoIDTask : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String): String {

            return "login"

        }

        override fun onPostExecute(result: String) {
            login_bt.isClickable = true
            login_pbar.visibility = View.GONE
            Toast.makeText(baseContext, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) { // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) { // Explain to the user why we need to write the permission.
                    Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show()
                }
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    100
                )
                // MY_PERMISSION_REQUEST_STORAGE is an
// app-defined int constant
            } else { // 다음 부분은 항상 허용일 경우에 해당이 됩니다.
            }
        }
    }

    internal inner class reloadGamesTask : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String): String {
            val su = SendServer()

            val url = "games"
            val postDataParams = JSONObject()

            return su.requestPOST(url,postDataParams)

        }

        override fun onPostExecute(result: String) {
            if (result == "") {
                Toast.makeText(baseContext, "서버 통신오류, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
//                Toast.makeText(baseContext, "디비  test 삽입", Toast.LENGTH_SHORT).show()
//
//                val addRunnable = Runnable {
//
//                    gameDatabase?.gameDao()?.deleteAll()
//                    val games: List<GameDB>? = gameDatabase?.gameDao()?.getGames()
//                    Log.d("bitx_log", "games :" + games)
//                    if (games!!.isEmpty()) {
//
//                        Log.d("bitx_log", "DB test in")
//
//                        val game = GameDB()
//                        game.name = "다이아몬드 게임"
//                        game.engName = "Diamond_Game"
//                        val str = "두뇌싸움,머리조금만쓰는"
//                        game.type = str
//                        game.expUrl =
//                            "https://youtu.be/5VkE9fEj7sM"
//                        game.people = "2,3"
//                        game.gameImgUrl ="https://blogfiles.pstatic.net/20151125_256/acolyte_1448463493847DA9xU_PNG/1.png"
//                        game.gameTime = "15분"
//                        game.expTime = "15:30"
//                        game.expImg = "1. 12개를 가지고 오세요 \n2. 카드를 가져오세요"
//                        game.level = "낮음"
//                        game.recommend = "추천"
//                        game.hit=1
//                        gameDatabase?.gameDao()?.insert(game)
//
//                        val game2 = GameDB()
//                        game2.name = "달무티 몬드"
//                        game2.engName = "katan"
//                        val str2 = "추리,구라,순발력"
//                        game2.type = str2
//                        game2.recommend = "추천"
//                        val numbers2 = intArrayOf(3, 4, 5, 6)
//                        game2.people = "3,4,5,6"
//                        game2.gameTime = "30분"
//                        game2.expTime = "11:00"
//                        game2.gameImgUrl ="https://blogfiles.pstatic.net/20151125_256/acolyte_1448463493847DA9xU_PNG/1.png"
//                        game2.hit=2
//                        game2.memo="2"
//                        game2.level = "중간";
//                        gameDatabase?.gameDao()?.insert(game2)
//
//                        val game3 = GameDB()
//                        game3.name = "뱅"
//                        game3.engName= "bang of bang"
//                        val str3 = "추리,구라"
//                        game3.type = str3
//                        game3.gameTime = "45"
//                        game3.gameImgUrl ="https://blogfiles.pstatic.net/20151125_256/acolyte_1448463493847DA9xU_PNG/1.png"
//                        game3.people = "6,7,8,9"
//                        game3.level = "어려움";
//                        game3.recommend = "비추천"
//                        game3.hit=1
//                        gameDatabase?.gameDao()?.insert(game3)
//
//                        val game4 = GameDB()
//                        game4.name = "루미큐브"
//                        val str4 = "추리,구라,퍼즐,심리"
//                        game4.level = "중간";
//                        game4.type = str4
//                        game4.gameTime = "60"
//                        game4.recommend = "비추천"
//                        game4.gameImgUrl ="https://blogfiles.pstatic.net/20151125_256/acolyte_1448463493847DA9xU_PNG/1.png"
//                        val numbers4 = intArrayOf(2, 3, 4, 5)
//                        game4.people = "2,3,4,5"
//                        game4.hit=15
//                        gameDatabase?.gameDao()?.insert(game4)
//                    }
//                    Log.d("bitx_log", "DB test in2" + gameDatabase?.gameDao()?.getGames())
//                }
//
//                val addThread = Thread(addRunnable)
//                addThread.start()

            }
            else {
                Toast.makeText(baseContext, "디비 업데이트", Toast.LENGTH_SHORT).show()
                try {
                    val `object` = JSONObject(result)

                val count = `object`.getInt("totalCount")
                val entities = `object`.getJSONArray("entities")
                if (count == 0) {

                }
                else
                {
                    for(i in 0 until count)
                    {
                        val addRunnable = Runnable {
                            val game = GameDB()

                            val json = entities.getJSONObject(i)
                            val gameID = json.getString("gameID")
                            val name = json.getString("name")
                            val gameTime = json.getString("gameTime")
                            val expTime = json.getString("expTime")
                            val expText = json.getString("expText")

//                            val expImg = json.getString("expImg")
//                            val expUrl = json.getString("expUrl")
                            val type = json.getString("type")
                            val level = json.getString("level")
                            val people = json.getString("people")
                            val recommend = json.getString("recommend")
                            val hit = json.getInt("hit")
                            if(json.has("memo")) game.memo = json.getString("memo")
                            if(json.has("engName")) game.engName = json.getString("engName")
                            if(json.has("expUrl")) game.expUrl = json.getString("expUrl")
                            if(json.has("expImg")) game.expImg = json.getString("expImg")
                            if(json.has("gameImgUrl")) game.gameImgUrl = json.getString("gameImgUrl")

                            game.name = name
                            game.gameTime = gameTime
                            game.expTime = expTime
                            game.expText = expText
                            game.type = type
                            game.level = level
                            game.recommend = recommend
                            game.hit = hit
                            game.people = people
                            gameDatabase?.gameDao()?.insert(game)


                        }
                        val addThread = Thread(addRunnable)
                        addThread.start()
                    }
                }
                }catch (e: Exception)
                {
                    Log.d("bitx_log","error + $e")
                }
            }

        }
    }


}