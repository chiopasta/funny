package com.bitxflow.funny

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitxflow.funny.biz.login.LoginAcitivty

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val nextIntent = Intent(this, LoginAcitivty::class.java)
        startActivity(nextIntent)
    }
}