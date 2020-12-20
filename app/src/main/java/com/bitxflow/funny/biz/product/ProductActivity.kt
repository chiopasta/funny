package com.bitxflow.funny.biz.product

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bitxflow.funny.DB.GameDatabase
import com.bitxflow.funny.DB.Product
import com.bitxflow.funny.R
import com.google.android.youtube.player.internal.i
import kotlinx.android.synthetic.main.activity_product.*
import java.lang.Exception
import java.util.*


class ProductActivity : AppCompatActivity() {
    var productList : ArrayList<Product> = ArrayList()
    var selectList : List<Product> = ArrayList()
    private var funnyDatabase : GameDatabase? = null
    var amount = 1
    var product_index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        funnyDatabase = GameDatabase.getInstance(baseContext)

        val addRunnable = Runnable {

            productList = funnyDatabase?.productDao()?.getProducts() as ArrayList<Product>
            selectList = productList.filter{it.type!!.contains("coffee")}

            /////SETTING THE FIRST
            var searchedList : ArrayList<Product> = ArrayList()
            searchedList.addAll(selectList)

            val adapter_select_list = ProductListAdapter(applicationContext , searchedList)
            adapter_select_list.notifyDataSetChanged()
            product_lv.adapter = adapter_select_list

        }
        val addThread = Thread(addRunnable)
        addThread.start()

        val arrayOfListView = ArrayList<String>()
        arrayOfListView.add("커피")
        arrayOfListView.add("디저트")
        arrayOfListView.add("스낵")
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayOfListView)
        type_lv.adapter = adapter
        type_lv.setOnItemClickListener{parent,view,position,id ->
            var type = ""
            when (position) {
                0 -> type = "coffee"
                1 -> type = "desert"
                2 -> type = "snack"
            }
            for(i in 0 until type_lv.childCount)
            {
                if(position == i )
                    type_lv.getChildAt(i).setBackgroundColor(Color.parseColor("#8C8C8C"))
                else
                    type_lv.getChildAt(i).setBackgroundColor(Color.TRANSPARENT)
            }

            //////SET///////////
            product_index = -1
            amount_lv.setItemChecked(0,true)
            amount = 1
            //////SET///////////

            selectList = productList.filter{it.type!!.contains(type)}
            var searchedList : ArrayList<Product> = ArrayList()
            searchedList.addAll(selectList)

            val adapter_select_list = ProductListAdapter(applicationContext , searchedList)
            adapter_select_list.notifyDataSetChanged()
            product_lv.adapter = adapter_select_list
        }

        product_lv.setOnItemClickListener{parent,view,position,id ->
            for(i in 0 until product_lv.childCount)
            {
                if(position == i )
                    product_lv.getChildAt(i).setBackgroundColor(Color.parseColor("#8C8C8C"))
                else
                    product_lv.getChildAt(i).setBackgroundColor(Color.TRANSPARENT)
            }
            product_index = position
            amount_lv.setItemChecked(0,true)
            amount = 1
        }

        val arrayOfAmount = ArrayList<String>()
        for(i in 1 .. 10)
        {
            arrayOfAmount.add(i.toString())
        }
        var adapterAmount = ArrayAdapter(this, android.R.layout.simple_list_item_checked, arrayOfAmount)
        amount_lv.adapter = adapterAmount
        amount_lv.setItemChecked(0,true)
        amount_lv.setOnItemClickListener{parent,view,position,id ->
            when (position) {
                0 -> amount = 1
                1 -> amount = 2
                2 -> amount = 3
                3 -> amount = 4
                4 -> amount = 5
                5 -> amount = 6
                6 -> amount = 7
                7 -> amount = 8
                8 -> amount = 9
                9 -> amount = 10
            }

        }


        pay_bt.setOnClickListener {
            try {
                if(product_index == -1)
                    Toast.makeText(baseContext, "선택을 다시해주세요", Toast.LENGTH_SHORT).show()
                else {
                    val price = selectList[product_index].price
                    if (price != null) {
                        Toast.makeText(
                            baseContext,
                            "결제금액은 " + price * amount + "입니다",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(baseContext, "선택을 다시해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (e : Exception)
            {
                Toast.makeText(baseContext, "선택을 다시해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }
}