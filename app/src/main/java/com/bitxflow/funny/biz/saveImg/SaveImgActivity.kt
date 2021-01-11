package com.bitxflow.funny.biz.saveImg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_save_img.*

class SaveImgActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_img)

        down_intro1_bt.setOnClickListener {
            save_wv.loadUrl("https://drive.google.com/file/d/1yJQXUNTMVfC54A96YmHR7tzHXrdeLKe1/view?usp=sharing")
        }

    }
}