package com.bitxflow.funny.biz.search

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.viewpager.widget.ViewPager
import com.bitxflow.funny.R
import com.bitxflow.funny.biz.intro.ViewPagerAdapter
import com.bitxflow.funny.biz.video.MemoryVideoView
import com.bitxflow.funny.biz.video.VideoActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_intro.*
import me.relex.circleindicator.CircleIndicator
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
        convertView: View?,
        parent: ViewGroup?
    ): View {
//        var convertView = convertView
        val game = member[position]
        val res: Int = R.layout.game_list
        val convertView = mInflater.inflate(res, parent, false)

        val engName = game.engName?.replace(" ","_")

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

        val people = game.people
        var ppl : String? = null
        if(people.size>2) {
            ppl = people.get(0).toString() + " - " + people.get(people.size - 1).toString() + "인"
        }
        else{
            ppl = people.get(0).toString() + "인"
        }
        people_tx.text = ppl

        val play_time = game.gameTime
        val exp_time = game.expTime
        val type = game.type[0]
        val memo = game.memo

        type_tx.text = type
        level_tx.text = game.level
        title_tx.text = game.name
        play_time_tx.text = play_time
        exp_time_tx.text = exp_time
        eng_title.text = engName

        setting_bt.setOnClickListener {
            val builder = AlertDialog.Builder(_activity)
            val dialogView = mInflater.inflate(R.layout.game_dialog,null)
            val dialog_tx =
                dialogView.findViewById<View>(R.id.dialog_tx) as TextView
            dialog_tx.text = game.expImg
            builder.setView(dialogView)
                .setNeutralButton("확인"){dialogInterface, i ->

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
                Intent.putExtra("engName", engName)
                _context.startActivity(Intent)
            }
        }

        expUrl_bt.setOnClickListener {
            try {
                val fileName =  Environment.getExternalStorageDirectory().absolutePath +"/Movies/"+engName+".mp4"
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

        //TODO 불러오는 순서, 이름이 있다면 메모리 체크, 그 이후 url 체크, 그 이후 loading 보여주기
//        UrlImageViewHelper.setUrlDrawable(img_iv, member[position].pUrl,R.drawable.profileimage)
        Glide.with(convertView).load(member[position].expImg).placeholder(R.drawable.loading).override(1000,600).into(img_iv)
        if(!engName.isNullOrBlank()) {
            val id = _context.resources.getIdentifier(engName, "drawable", _context.packageName)
            if (id > 0) {
                img_iv.setImageResource(id)
                Log.d("bitx_log", "exists drawable!")
            } else {
                Glide.with(convertView).load(member[position].expImg)
                    .placeholder(R.drawable.loading).override(1000, 600).into(img_iv)
            }
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