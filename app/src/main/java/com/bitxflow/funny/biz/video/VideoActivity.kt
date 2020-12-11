package com.bitxflow.funny.biz.video

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val intent = intent
        val url = intent.extras!!.getString("url").toString()

        val replaceUrl = url.replace("https://youtu.be/","")
        you_tube_player_view.play(replaceUrl)

    }
}