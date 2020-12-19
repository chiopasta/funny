package com.bitxflow.funny.biz.product

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bitxflow.funny.DB.Product
import com.bitxflow.funny.R
import java.util.*


class ProductListAdapter(
    context: Context,
    productList: ArrayList<Product>
) :
    BaseAdapter() {
    var mInflater: LayoutInflater
    var productList: ArrayList<Product>
    var _context: Context

    override fun getCount(): Int {
        return productList.size
    }

    override fun getItem(position: Int): Any {
        return productList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val product = productList[position]
        val res: Int = R.layout.product_list
        val convertView = mInflater.inflate(res, parent, false)

        val nameTx =
            convertView.findViewById<View>(R.id.product_name_tx) as TextView

        val priceTx =
            convertView.findViewById<View>(R.id.price_tx) as TextView

        val descriptionTx =
            convertView.findViewById<View>(R.id.description_tx) as TextView

        val price = product.price
        nameTx.text = product.name
        priceTx.text = price.toString()
        descriptionTx.text = product.description


        return convertView
    }

    init {
        mInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.productList = productList
        _context = context
    }
}