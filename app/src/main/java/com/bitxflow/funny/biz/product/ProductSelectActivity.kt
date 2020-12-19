package com.bitxflow.funny.biz.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_product_select.*

class ProductSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_select)

        val intent = intent
        val name = intent.extras!!.getString("name").toString()
        val price = intent.extras!!.getInt("price")

        select_name.text = name
        select_price.text = price.toString()
    }
}