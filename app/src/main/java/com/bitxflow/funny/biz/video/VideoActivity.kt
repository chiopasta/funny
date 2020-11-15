package com.bitxflow.funny.biz.video

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val intent = intent
        val url = intent.extras!!.getString("url").toString()
        Log.d("bitx_log","url? : $url")

        video_view.loadUrl(url)
    }
}