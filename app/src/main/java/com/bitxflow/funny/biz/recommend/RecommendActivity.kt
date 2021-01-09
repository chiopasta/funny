package com.bitxflow.funny.biz.recommend

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import com.bitxflow.funny.DB.GameDB
import com.bitxflow.funny.DB.GameDatabase
import com.bitxflow.funny.R
import com.bitxflow.funny.biz.search.Game
import com.bitxflow.funny.biz.search.GameListAdapter
import com.bitxflow.funny.biz.search.HangulUtils
import kotlinx.android.synthetic.main.activity_recommend.*
import kotlinx.android.synthetic.main.search_header.*
import java.util.ArrayList

class RecommendActivity : AppCompatActivity() {
    var gameList : ArrayList<Game> = ArrayList()
    var searchGamest : List<Game> = ArrayList()
    private var gameDatabase : GameDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)

        var ppl = 0
        var time = ""
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
                    game.hit = hit!!.toInt()
                    game.memo = memo

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

            searchGamest = gameList.filter{ it.recommend!! == "추천" }

            var searchedList : ArrayList<Game> = ArrayList()
            searchedList.addAll(searchGamest)
            val adapter = GameListAdapter(applicationContext , searchedList, RecommendActivity@this)
            adapter.notifyDataSetChanged()
            recommend_listview.adapter = adapter
        }
        val addThread = Thread(addRunnable)
        addThread.start()

        val header: View =
            layoutInflater.inflate(R.layout.search_header, null, false)

//        var searchedList : ArrayList<Game> = ArrayList()
//        searchedList.addAll(searchGamest)
//        val adapter = GameListAdapter(applicationContext , searchedList, RecommendActivity@this)
//        adapter.notifyDataSetChanged()
//        recommend_listview.adapter = adapter
        recommend_listview.addHeaderView(header)

        nav_rl.setOnClickListener{
            it.hideKeyboard()
        }
        header.setOnClickListener {
            it.hideKeyboard()
        }

        search_back_bt.setOnClickListener {
            finish()
        }

        var isChecking = true

        type_rg.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                if(type_rg2.checkedRadioButtonId != -1) // checked
                    type_rg2.clearCheck()
                if(type_rg3.checkedRadioButtonId != -1)
                    type_rg3.clearCheck()
            }
            isChecking = true
        })

        type_rg2.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                if(type_rg.checkedRadioButtonId != -1)
                    type_rg.clearCheck()
                if(type_rg3.checkedRadioButtonId != -1)
                    type_rg3.clearCheck()
            }
            isChecking = true
        }

        type_rg3.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false

                if(type_rg.checkedRadioButtonId != -1)
                    type_rg.clearCheck()
                if(type_rg2.checkedRadioButtonId != -1)
                    type_rg2.clearCheck()

            }
            isChecking = true
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

                when(time_rg.checkedRadioButtonId){
                    R.id.time0_radio_bt -> time = ""
                    R.id.time_10 -> time = "10"
                    R.id.time_15 -> time = "15"
                    R.id.time_20 -> time = "20"
                    R.id.time_30 -> time = "30"
                    R.id.time_45 -> time = "45"
                    R.id.time_60 -> time = "60"
                }

                when(level_rg.checkedRadioButtonId){
                    R.id.level0_radio_bt -> level= ""
                    R.id.level1_radio_bt -> level= "낮음"
                    R.id.level2_radio_bt -> level= "중간"
                    R.id.level3_radio_bt -> level= "높음"
                }

                when(type_rg.checkedRadioButtonId){
                    R.id.type0_radio_bt -> type= ""
                    R.id.type_clue -> type= "추리"
                    R.id.type_simri -> type= "심리"
                    R.id.type_strategy -> type= "전략"
                    R.id.type_nego -> type= "협상"
                    R.id.type_speed -> type= "순발력"
                    R.id.type_binjung -> type= "빈정상함"
                }

                when(type_rg2.checkedRadioButtonId){
                    R.id.type_economy -> type= "경제"
                    R.id.type_memory -> type= "기억력"
                    R.id.type_teamplay -> type= "팀플"
                    R.id.type_cowork -> type= "협력"
                    R.id.type_luck -> type= "운빨"
                    R.id.type_mapia -> type= "마피아"
                    R.id.type_brain -> type= "두뇌싸움"
                    R.id.type_betting -> type= "배팅"
                    R.id.type_bluffing -> type= "뻥치기"
                }

                when(type_rg3.checkedRadioButtonId){
                    R.id.type_nobrain -> type= "머리조금만쓰는"
                    R.id.type_party -> type= "파티"
                    R.id.type_tackle -> type= "딴지"
                    R.id.type_knowlege -> type= "지식"
                    R.id.type_puzzle -> type= "퍼즐"
                }

                search_gameList = searchGamest.filter{it.people.contains(ppl)}

                if(!level.isNullOrBlank()) {
                    search_gameList = search_gameList.filter { it.level!!.contains(level) }
                }
                if(!time.isNullOrBlank()) {
                    search_gameList = search_gameList.filter { it.gameTime!!.contains(time) }
                }
                if(!type.isNullOrBlank()) {
                    search_gameList = search_gameList.filter { it.type.contains(type) }
                }
                var searchedList : ArrayList<Game> = ArrayList()
                searchedList.addAll(search_gameList.sortedBy { it.name })
                val adapter = GameListAdapter(applicationContext , searchedList, RecommendActivity@this)
                adapter.notifyDataSetChanged()
                recommend_listview.adapter = adapter
            }
            else //////////////이름검색
            {
                var temp_gameList : ArrayList<Game> = ArrayList()
                for(items in searchGamest)
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
                val adapter = GameListAdapter(applicationContext , temp_gameList,RecommendActivity@this)
                adapter.notifyDataSetChanged()
                recommend_listview.adapter = adapter
            }
        }
    }

        fun View.hideKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }

}