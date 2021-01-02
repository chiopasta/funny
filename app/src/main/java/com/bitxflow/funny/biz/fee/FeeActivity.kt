package com.bitxflow.funny.biz.fee

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_fee.*
import java.io.File

@Suppress("DEPRECATION")
class FeeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fee)

        back_bt.setOnClickListener {
            finish()
        }

        var fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Pictures/fee.jpg"
        var file = File(fileName)
        if(file.exists())
        {
            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
            fee_iv.setImageBitmap(myBitmap)
        }
        fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Pictures/fee.png"
        file = File(fileName)
        if(file.exists())
        {
            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
            fee_iv.setImageBitmap(myBitmap)
        }
        fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Pictures/fee.jpeg"
        file = File(fileName)
        if(file.exists())
        {
            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
            fee_iv.setImageBitmap(myBitmap)
        }
        fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Pictures/fee.bmp"
        file = File(fileName)
        if(file.exists())
        {
            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
            fee_iv.setImageBitmap(myBitmap)
        }
    }
}