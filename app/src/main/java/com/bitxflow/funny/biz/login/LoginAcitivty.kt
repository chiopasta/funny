package com.bitxflow.funny.biz.login

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bitxflow.funny.DB.GameDB
import com.bitxflow.funny.DB.GameDatabase
import com.bitxflow.funny.R
import com.bitxflow.funny.send.SendServer
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLEngine

class LoginAcitivty : AppCompatActivity() {

    private var gameDatabase : GameDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkPermission()
        gameDatabase = GameDatabase.getInstance(baseContext)

        login_bt.setOnClickListener{
            val id = username.text.toString()
            val pw = password.text.toString()
            Log.d("bitx_log","clicked")
            login_pbar.visibility = View.VISIBLE
            SendTask().execute(id,pw)
            login_bt.isClickable = false
        }

        reload_bt.setOnClickListener {
            reloadGamesTask().execute()
        }
    }

    internal inner class SendTask : AsyncTask<String, String, String>() {
        var userId : String =""
        var userPassword : String = ""

        override fun doInBackground(vararg params: String): String {
            val su = SendServer()
            userId = params[0].trim()
            userPassword = params[1].trim()

            val url = "login"
            val postDataParams = JSONObject()
            postDataParams.put("userid", userId.toUpperCase())
            postDataParams.put("password", userPassword)

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
                setResult(Activity.RESULT_OK, Intent().putExtra("BackPress", userId))
                finish()
//                if(success)
//                {
//                    val addRunnable = Runnable {
//
//                        userDB = MemberDatabase.getInstance(baseContext)
//
//                        ////LOGIN 된 유져가 있다면 multy = false
//                        val users : List<User>? =  userDB?.userDao()?.getUsers()
//                        if(users!!.isNotEmpty()) {
//                            var login_user: User? = userDB?.userDao()?.getMultyLoginUser(true)
//                            if (login_user!!.multy_login!!) {
//                                login_user.multy_login = false
//                                userDB?.userDao()?.update(login_user)
//                                Log.d("bitx_log", "login 유져 존재함")
//                            }
//                        }
//
//                        //////////////////new USER insert //////////////
//                        val user = User()
//                        user.userId = userId
//                        user.userPassword = userPassword
//                        user.userName = ""
//                        user.classSid = ""
//                        user.className = ""
//                        user.imgSrc=""
//                        user.multy_login = true
//
//                        userDB?.userDao()?.insert(user)
//                        Log.d("bitx_log","userDB insert" + userId)
//                        Log.d("bitx_log","size?" + userDB?.userDao()?.getUsers()!!.size)
//
//                    }
//
//                    val addThread = Thread(addRunnable)
//                    addThread.start()
//
//                    setResult(Activity.RESULT_OK, Intent().putExtra("BackPress", userId))
//                    finish()
//                }
//                else {
//                    Toast.makeText(baseContext, json.getString("message"), Toast.LENGTH_SHORT).show()
//                }
            }
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
            return su.requestGET(url)

        }

        override fun onPostExecute(result: String) {
            if (result == "") {
//                Toast.makeText(baseContext, "서버 통신오류, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                Toast.makeText(baseContext, "디비 삽입", Toast.LENGTH_SHORT).show()

                val addRunnable = Runnable {

                    gameDatabase?.gameDao()?.deleteAll()
                    val games: List<GameDB>? = gameDatabase?.gameDao()?.getGames()
                    Log.d("bitx_log", "games :" + games)
                    if (games!!.isEmpty()) {

                        Log.d("bitx_log", "DB test in")

                        val game = GameDB()
                        game.name = "할리갈리"
                        val str = "순발력,퍼즐"
                        game.type = str
                        game.expUrl =
                            "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxOTEyMDNfMjA4%2FMDAxNTc1MzUyNDU0NjU2.5baBn7OpRGonQIUUlrpjVOusF4g4UAek4j_PqhZ2JGcg.Wvnz79SMBX51Hhq_eiR_6hvlDT1zCQpP2SUj29cjP2Mg.JPEG.qnsegtgk%2F%2525B7%2525E7%2525B9%2525CC%2525C5%2525A5%2525BA%2525EA%252B%2525C6%2525AE%2525B7%2525A1%2525BA%2525ED.jpg&type=sc960_832"
                        game.people = "2,3,4,5,6"
                        game.level = "쉬움"
                        game.recommend = "추천"
                        game.hit=1
                        gameDatabase?.gameDao()?.insert(game)

                        val game2 = GameDB()
                        game2.name = "카탄"
                        game2.engName = "katan"
                        val str2 = "추리,구라,순발력"
                        game2.type = str2
                        game2.recommend = "추천"
                        val numbers2 = intArrayOf(3, 4, 5, 6)
                        game2.people = "3,4,5,6"
                        game2.hit=2
                        game2.level = "중간";
                        gameDatabase?.gameDao()?.insert(game2)

                        val game3 = GameDB()
                        game3.name = "뱅"
                        val str3 = "추리,구라"
                        game3.type = str3

                        game3.people = "6,7,8,9"
                        game3.level = "어려움";
                        game3.recommend = "비추천"
                        game3.hit=1
                        gameDatabase?.gameDao()?.insert(game3)
                        val game4 = GameDB()
                        game4.name = "루미큐브"
                        val str4 = "추리,구라,퍼즐,심리"
                        game4.level = "중간";
                        game4.type = str4
                        game4.recommend = "비추천"
                        val numbers4 = intArrayOf(2, 3, 4, 5)
                        game4.people = "2,3,4,5"
                        game4.hit=15
                        gameDatabase?.gameDao()?.insert(game4)
                    }
                    Log.d("bitx_log", "DB test in2" + gameDatabase?.gameDao()?.getGames())
                }

                val addThread = Thread(addRunnable)
                addThread.start()

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