package com.bitxflow.funny.biz.video

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.MediaController
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_memory_video_view.*

class MemoryVideoView : AppCompatActivity() {

    var mediaControls: MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_video_view)

        val intent = intent
        val fileName = intent.extras!!.getString("fileName").toString()

        Log.d("bitx_log","filename"+ fileName)
        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(this)

            // set the anchor view for the video view
            mediaControls!!.setAnchorView(this.memory_vv)
        }

        val vidFile = Uri.parse(fileName)

        memory_vv!!.setMediaController(mediaControls)

        // set the absolute path of the video file which is going to be played
        memory_vv!!.setVideoURI(vidFile)

        memory_vv!!.requestFocus()

        // starting the video
        memory_vv!!.start()
    }
}