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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkPermission()


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

}