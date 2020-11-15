package com.bitxflow.funny.biz.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.bitxflow.funny.R
import com.bitxflow.funny.biz.video.VideoActivity
import com.bumptech.glide.Glide
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
        //TODO url button 으로 바꿔야함 !!!
        title_tx.setOnClickListener {
            val Intent = Intent(_context, VideoActivity::class.java)
            Intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            Intent.putExtra("url",member[position].expUrl)
            _context.startActivity(Intent)
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(member[position].expUrl))
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            _context.startActivity(intent)
        }

//        contents_tx.text = Html.fromHtml(replyContent)
//        contents_tx.movementMethod = LinkMovementMethod.getInstance()

//        UrlImageViewHelper.setUrlDrawable(img_iv, member[position].pUrl,R.drawable.profileimage)
        Glide.with(convertView).load(member[position].expImg).placeholder(R.drawable.loading).override(1000,600).into(img_iv)

        return convertView
    }

    init {
        mInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        member = photoListItem
        _context = context
    }
}