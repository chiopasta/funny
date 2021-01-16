package com.bitxflow.funny.biz.beginner

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_beginner.*
import java.io.File

class BeginnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beginner)

//        var fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Download/easy.jpg"
//        var file = File(fileName)
//        if(file.exists())
//        {
//            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
//            beginner_iv.setImageBitmap(myBitmap)
//        }
//        fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Download/easy.png"
//        file = File(fileName)
//        if(file.exists())
//        {
//            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
//            beginner_iv.setImageBitmap(myBitmap)
//        }
//        fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Download/easy.jpeg"
//        file = File(fileName)
//        if(file.exists())
//        {
//            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
//            beginner_iv.setImageBitmap(myBitmap)
//        }
//        fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Download/easy.bmp"
//        file = File(fileName)
//        if(file.exists())
//        {
//            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
//            beginner_iv.setImageBitmap(myBitmap)
//        }

        beginner_back_bt.setOnClickListener {
            finish()
        }
    }
}