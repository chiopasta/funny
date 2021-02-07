package com.bitxflow.funny.biz.snack

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bitxflow.funny.R
import java.io.File

class ViewPagerAdapter(private val context : Context) : PagerAdapter() {

    private var layoutInflater : LayoutInflater? = null
    val Image = arrayOf(
        R.drawable.snack3,
        R.drawable.snack0,
        R.drawable.snack1,
        R.drawable.snack2
    )


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view ===  `object`
    }

    override fun getCount(): Int {
        return 4
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.snack_img_slide, null)
        val image = v.findViewById<View>(R.id.img_slide_iv) as ImageView
        image.setImageResource(Image[position])
//        var fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Download/snack" + position.toString()+".jpg"
//        var file = File(fileName)
//        if(file.exists())
//        {
//            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
//            image.setImageBitmap(myBitmap)
//        }
//        fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Download/snack" + position.toString()+".png"
//        file = File(fileName)
//        if(file.exists())
//        {
//            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
//            image.setImageBitmap(myBitmap)
//        }
//        fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Download/snack" + position.toString()+".jpeg"
//        file = File(fileName)
//        if(file.exists())
//        {
//            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
//            image.setImageBitmap(myBitmap)
//        }
//        fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Download/snack" + position.toString()+".bmp"
//        file = File(fileName)
//        if(file.exists())
//        {
//            val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
//            image.setImageBitmap(myBitmap)
//        }



        val vp = container as ViewPager
        vp.addView(v , 0)


        return v

    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }
}