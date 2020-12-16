package com.bitxflow.funny.biz.search

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bitxflow.funny.DB.GameDB
import com.bitxflow.funny.DB.GameDatabase
import com.bitxflow.funny.R
import com.bitxflow.funny.biz.video.MemoryVideoView
import com.bitxflow.funny.send.SendServer
import com.google.android.youtube.player.internal.i
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.search_header.*
import org.json.JSONObject
import java.util.ArrayList

class SearchActivity : AppCompatActivity() {
    var gameList : ArrayList<Game> = ArrayList()
    private var gameDatabase : GameDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var ppl = 0;
        var level = ""
        var type = ""

        gameDatabase = GameDatabase.getInstance(baseContext)

        val addRunnable = Runnable {

            val games : List<GameDB>? = gameDatabase?.gameDao()?.getGames()
            if(games!!.isNotEmpty())
            {
                for(item in games)
                {
                    item.name
                    val gameID = item.id
                    val name = item.name
                    val engName = item.engName
                    val gameTime = item.gameTime
                    val expTime = item.expTime
                    val expText = item.expText
                    val expImg = item.expImg
                    val expUrl = item.expUrl
                    val gameImgUrl = item.gameImgUrl
                    val type = item.type
                    val level = item.level
                    val people = item.people
                    val recommend = item.recommend
                    val hit = item.hit
                    val memo = item.memo

                    val game = Game()
                    game.gameID = gameID.toString()
                    game.name = name
                    game.engName = engName
                    game.gameTime = gameTime
                    game.expTime = expTime
                    game.expText = expText
                    game.expUrl = expUrl
                    game.expImg = expImg
                    game.type = type!!.split(",")
                    game.level = level
                    game.recommend = recommend
                    game.hit = hit!!
                    game.memo = memo
                    game.gameImgUrl = gameImgUrl

                    val strPpl = people!!.split(",")
                    var numbers = ArrayList<Int>()
                    for(element in strPpl)
                    {
                        numbers.add(Integer.parseInt(element))
                    }
                    game.people = numbers.toIntArray()
                    gameList.add(game)
                }
            }
        }
        val addThread = Thread(addRunnable)
        addThread.start()

        val header: View =
            layoutInflater.inflate(R.layout.search_header, null, false)

        var baseArrayList : ArrayList<String> = ArrayList()
        baseArrayList.add("검색해주세요")
        val adapter = ArrayAdapter(applicationContext , android.R.layout.simple_list_item_1,baseArrayList)
        adapter.notifyDataSetChanged()
        game_listview.adapter = adapter
        game_listview.addHeaderView(header)

        nav_rl.setOnClickListener{
            it.hideKeyboard()
        }
        header.setOnClickListener {
            it.hideKeyboard()
        }

        search_back_bt.setOnClickListener {
            finish()
        }

        search_bt.setOnClickListener {

            it.hideKeyboard()

            val search_title = search_et.text.toString()

            var search_gameList : List<Game>

            ///////////////////// 조건 검색
            if(search_title == "")
            {

                when(ppl_rg.checkedRadioButtonId){
                    R.id.ppl2_radio_bt -> ppl=2
                    R.id.ppl3_radio_bt -> ppl=3
                    R.id.ppl4_radio_bt -> ppl=4
                    R.id.ppl5_radio_bt -> ppl=5
                    R.id.ppl6_radio_bt -> ppl=6
                    R.id.ppl7_radio_bt -> ppl=7
                    R.id.ppl8_radio_bt -> ppl=8
                }

                when(level_rg.checkedRadioButtonId){
                    R.id.level0_radio_bt -> level= ""
                    R.id.level1_radio_bt -> level= "쉬움"
                    R.id.level2_radio_bt -> level= "중간"
                    R.id.level3_radio_bt -> level= "어려움"
                }

                when(type_rg.checkedRadioButtonId){
                    R.id.type0_radio_bt -> type= ""
                    R.id.type_gura -> type= "구라"
                    R.id.type_inference -> type= "추리"
                    R.id.type_speed -> type= "순발력"
                }

                search_gameList = gameList.filter{it.people.contains(ppl)}

                if(!level.isNullOrBlank()) {
                    search_gameList = search_gameList.filter { it.level!!.contains(level) }
                }
                if(!type.isNullOrBlank()) {
                    search_gameList = search_gameList.filter { it.type.contains(type) }
                }
                var searchedList : ArrayList<Game> = ArrayList()
                searchedList.addAll(search_gameList)
                val adapter = GameListAdapter(applicationContext , searchedList, SearchActivity@this)
                adapter.notifyDataSetChanged()
                game_listview.adapter = adapter
            }
            else //////////////이름검색
            {
                var temp_gameList : ArrayList<Game> = ArrayList()
                for(items in gameList)
                {
                    var isAdd = false
                    val ini = HangulUtils.getHangulInitialSound(items.name,search_title)
                    if(ini.indexOf(search_title) >= 0)
                    {
                        isAdd= true
                    }
                    if(isAdd)
                    {
                        temp_gameList.add(items)
                    }
                }
                val adapter = GameListAdapter(applicationContext , temp_gameList,SearchActivity@this)
                adapter.notifyDataSetChanged()
                game_listview.adapter = adapter
            }
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


}