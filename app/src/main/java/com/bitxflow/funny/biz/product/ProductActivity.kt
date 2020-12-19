package com.bitxflow.funny.biz.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bitxflow.funny.DB.GameDB
import com.bitxflow.funny.DB.GameDatabase
import com.bitxflow.funny.DB.Product
import com.bitxflow.funny.R
import com.bitxflow.funny.biz.search.Game
import kotlinx.android.synthetic.main.activity_product.*
import java.util.ArrayList

class ProductActivity : AppCompatActivity() {
    var productList : ArrayList<Product> = ArrayList()
    private var funnyDatabase : GameDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        funnyDatabase = GameDatabase.getInstance(baseContext)

        val addRunnable = Runnable {

            productList = funnyDatabase?.productDao()?.getProducts() as ArrayList<Product>
            Log.d("bitx_log","games " + productList.toString())
        }

        val addThread = Thread(addRunnable)
        addThread.start()

        coffee_bt.setOnClickListener {
            val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
        }
    }
}