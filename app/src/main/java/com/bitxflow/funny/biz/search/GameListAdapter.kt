package com.bitxflow.funny.biz.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toFile
import com.bitxflow.funny.R
import com.bitxflow.funny.biz.video.MemoryVideoView
import com.bitxflow.funny.biz.video.VideoActivity
import com.bumptech.glide.Glide
import java.io.File
import java.util.*


class GameListAdapter(
    context: Context,
    photoListItem: ArrayList<Game>
) :
    BaseAdapter() {
    var mInflater: LayoutInflater
    var member: ArrayList<Game>
    var _context: Context
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
        val res: Int = R.layout.game_list
        val convertView = mInflater.inflate(res, parent, false)
        val engName = member[position].engName?.replace(" ","_")
        Log.d("bitx_log","engName ? $engName")
        val title_tx =
            convertView.findViewById<View>(R.id.game_list_name) as TextView
//        val contents_tx =
//            convertView.findViewById<View>(R.id.gmae) as TextView
        val img_iv =
            convertView.findViewById<View>(R.id.game_list_iv) as ImageView
        val expUrl_bt =
            convertView.findViewById<View>(R.id.exp_url_bt) as Button
//        val replyContent: String? = member[position].contents
        title_tx.text = member[position].name

        expUrl_bt.setOnClickListener {
            try {
                val fileName =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/Movies/"+engName+".mp4"
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

//            val Intent = Intent(_context, MemoryVideoView::class.java)
//            Intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
//            Intent.putExtra("url",member[position].expUrl)
//            _context.startActivity(Intent)

//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(member[position].expUrl))
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            _context.startActivity(intent)
        }
//        contents_tx.text = Html.fromHtml(replyContent)
//        contents_tx.movementMethod = LinkMovementMethod.getInstance()

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