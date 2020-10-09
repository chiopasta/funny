package com.bitxflow.funny.biz.login

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
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginAcitivty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_bt.setOnClickListener{
            val id = username.text.toString()
            val pw = password.text.toString()
            Log.d("bitx_log","clicked")
            login_pbar.visibility = View.VISIBLE
            SendTask().execute(id,pw)
            login_bt.isClickable = false
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
}