package com.bitxflow.funny.biz.fee

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_fee.*

class FeeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fee)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fee_iv.setImageDrawable(resources.getDrawable(R.drawable.fee, baseContext.theme))
        }
        else{
            fee_iv.setImageDrawable(resources.getDrawable(R.drawable.fee))
        }
    }
}