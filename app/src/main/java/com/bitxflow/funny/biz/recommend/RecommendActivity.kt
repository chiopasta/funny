package com.bitxflow.funny.biz.recommend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bitxflow.funny.DB.GameDB
import com.bitxflow.funny.DB.GameDatabase
import com.bitxflow.funny.R
import com.bitxflow.funny.biz.search.Game
import com.bitxflow.funny.biz.search.GameListAdapter
import kotlinx.android.synthetic.main.activity_recommend.*
import kotlinx.android.synthetic.main.activity_search.*
import java.util.ArrayList

class RecommendActivity : AppCompatActivity() {
    var gameList : ArrayList<Game> = ArrayList()
    private var gameDatabase : GameDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)

        gameDatabase = GameDatabase.getInstance(baseContext)

        val addRunnable = Runnable {

            val games : List<GameDB>? = gameDatabase?.gameDao()?.getGames()
            Log.d("bitx_log","games::" + games)
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
                    Log.d("bitx_log","game: " + game)
                }
            }
            var search_gameList : List<Game>
            Log.d("bitx_log","gameList: " + gameList)

            search_gameList = gameList.filter{it.recommend!!.equals("추천")}

            Log.d("bitx_log","search_gameList: " + search_gameList)
            var searchedList : ArrayList<Game> = ArrayList()
            searchedList.addAll(search_gameList)
            val adapter = GameListAdapter(applicationContext , searchedList)
            adapter.notifyDataSetChanged()
            recommend_listview.adapter = adapter

        }
        val addThread = Thread(addRunnable)
        addThread.start()



    }
}