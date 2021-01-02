package com.bitxflow.funny.biz.search

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bitxflow.funny.R
import com.bitxflow.funny.biz.video.MemoryVideoView
import com.bitxflow.funny.biz.video.VideoActivity
import com.bumptech.glide.Glide
import java.io.File
import java.util.*


class GameListAdapter(
    context: Context,
    photoListItem: ArrayList<Game>,
    act : Activity
) :
    BaseAdapter() {
    var mInflater: LayoutInflater
    var member: ArrayList<Game>
    var _context: Context
    var _activity = act

    override fun getCount(): Int {
        return member.size
    }

    override fun getItem(position: Int): Any {
        return member[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        mView: View?,
        parent: ViewGroup?
    ): View {
        val game = member[position]
        val res: Int = R.layout.game_list
        val convertView = mInflater.inflate(res, parent, false)
        val name = game.name?.replace(" ","")

        val title_tx =
            convertView.findViewById<View>(R.id.game_list_name) as TextView
        val eng_title =
            convertView.findViewById<View>(R.id.eng_title) as TextView
        val img_iv =
            convertView.findViewById<View>(R.id.game_list_iv) as ImageView
        val expUrl_bt =
            convertView.findViewById<View>(R.id.exp_url_bt) as Button
        val exp_txt_bt =
            convertView.findViewById<View>(R.id.exp_txt_bt) as Button
        val setting_bt =
            convertView.findViewById<View>(R.id.setting_bt) as Button
        val people_tx =
            convertView.findViewById<View>(R.id.people_tx) as TextView
        val play_time_tx =
            convertView.findViewById<View>(R.id.play_time_tx) as TextView
        val exp_time_tx =
            convertView.findViewById<View>(R.id.exp_game_time_tx) as TextView
        val type_tx  =
            convertView.findViewById<View>(R.id.type_tx) as TextView
        val level_tx =
            convertView.findViewById<View>(R.id.level_tx) as TextView
        val content =
            convertView.findViewById<View>(R.id.content) as TextView
        val game_list_hit =
            convertView.findViewById<View>(R.id.game_list_hit) as ImageView

        val people = game.people
        val gameImgUrl = game.gameImgUrl

        var ppl : String?
        ppl = if(people.size>2) {
            people[0].toString() + " - " + people[people.size - 1].toString() + "인"
        } else{
            people[0].toString() + "인"
        }
        people_tx.text = ppl

        val playTime = game.gameTime
        val expTime = game.expTime
        val type = game.type[0]
        val memo = game.memo

        type_tx.text = type
        level_tx.text = game.level
        title_tx.text = game.name
        content.text = game.expText
        play_time_tx.text = playTime
        exp_time_tx.text = expTime
        eng_title.text = game.engName

        if(game.recommend == "추천")
        {
            game_list_hit.visibility = View.VISIBLE
        }
        else
        {
            game_list_hit.visibility = View.GONE
        }

        setting_bt.setOnClickListener {
            val builder = AlertDialog.Builder(_activity)
            val dialogView = mInflater.inflate(R.layout.game_dialog,null)
            val dialog_tx =
                dialogView.findViewById<View>(R.id.dialog_tx) as TextView
            dialog_tx.text = game.expImg
            builder.setView(dialogView)
                .setNeutralButton("확인"){_, _ ->

                }
                .show()

        }

        exp_txt_bt.setOnClickListener {
            if(memo.isNullOrBlank())
            {
                Toast.makeText(_context,"준비중 입니다",Toast.LENGTH_SHORT).show()
            }
            else {
                val Intent = Intent(_context, RuleActivity::class.java)
                Intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                Intent.putExtra("memo", memo)
                Intent.putExtra("name", name)
                _context.startActivity(Intent)
            }
        }
        @Suppress("DEPRECATION")
        expUrl_bt.setOnClickListener {
            try {
                val fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Movies/"+name+".mp4"
                var file = File(fileName)
                if(file.exists())
                {
                    val Intent = Intent(_context, MemoryVideoView::class.java)
                    Intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                    Intent.putExtra("fileName",fileName)
                    _context.startActivity(Intent)
                }
                else
                {
                    val Intent = Intent(_context, VideoActivity::class.java)
                    Intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                    Intent.putExtra("url",member[position].expUrl)
                    _context.startActivity(Intent)
                }
            }catch (e: Exception){
                Log.d("bitx_log", "e: $e")
            }

        }

        ///////////////// GAME IMG /////////////////////////////
        @Suppress("DEPRECATION")
        if(!name.isNullOrBlank()) {
//            val id = _context.resources.getIdentifier(engName, "drawable", _context.packageName)
//            if (id > 0) {
//                img_iv.setImageResource(id)
//            } else {
//                Glide.with(convertView).load(gameImgUrl).placeholder(R.drawable.loading).override(1000, 600).into(img_iv)
//            }
            Log.d("bitx_log","name : $name")

            var isFileExist = false
            var fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Pictures/"+ name +".jpg"
            var file = File(fileName)
            if(file.exists())
            {
                val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
                img_iv.setImageBitmap(myBitmap)
                isFileExist=true
            }
            fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Pictures/"+ name +".png"
            file = File(fileName)
            if(file.exists())
            {
                val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
                img_iv.setImageBitmap(myBitmap)
                isFileExist = true
            }
            fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Pictures/"+ name +".jpeg"
            file = File(fileName)
            if(file.exists())
            {
                val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
                img_iv.setImageBitmap(myBitmap)
                isFileExist = true
            }
            fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Pictures/"+ name +".bmp"
            file = File(fileName)
            if(file.exists())
            {
                val myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath())
                img_iv.setImageBitmap(myBitmap)
                isFileExist = true
            }
            if(!isFileExist)
            {
                Glide.with(convertView).load(gameImgUrl).placeholder(R.drawable.loading).override(1000, 600).into(img_iv)
            }
        }
        else{
            Glide.with(convertView).load(gameImgUrl).placeholder(R.drawable.loading).override(1000,600).into(img_iv)
        }
        return convertView
    }

    init {
        mInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        member = photoListItem
        _context = context
    }
}