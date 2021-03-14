package com.bitxflow.funny.biz.appdown

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.webkit.DownloadListener
import androidx.appcompat.app.AppCompatActivity
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_app_down.*


class AppDownActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_down)

        webview.loadUrl("https://www.funtime.kr/app.apk")

        webview.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://www.funtime.kr/app.apk")
            startActivity(i)
        })


    }

}