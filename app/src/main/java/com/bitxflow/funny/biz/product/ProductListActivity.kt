package com.bitxflow.funny.biz.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.bitxflow.funny.DB.GameDatabase
import com.bitxflow.funny.DB.Product
import com.bitxflow.funny.R
import com.bitxflow.funny.biz.search.GameListAdapter
import kotlinx.android.synthetic.main.activity_product_list.*
import java.util.ArrayList

class ProductListActivity : AppCompatActivity() {
    var productList : ArrayList<Product> = ArrayList()
    private var funnyDatabase : GameDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        funnyDatabase = GameDatabase.getInstance(baseContext)

        val addRunnable = Runnable {

            productList = funnyDatabase?.productDao()?.getProducts() as ArrayList<Product>

            val adapter = ProductListAdapter(applicationContext , productList)
            adapter.notifyDataSetChanged()
            product_lv.adapter = adapter
            
            product_lv.setOnItemClickListener{parent,view,position,id ->
                Log.d("bitx_log","position ?" + position)
                val intent = Intent(this, ProductSelectActivity::class.java)
                intent.putExtra("productID",productList[position].productID)
                intent.putExtra("name",productList[position].name)
                intent.putExtra("price",productList[position].price)
                startActivity(intent)
            }
        }

        
        
        val addThread = Thread(addRunnable)
        addThread.start()


    }
}