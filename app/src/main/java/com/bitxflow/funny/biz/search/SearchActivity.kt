package com.bitxflow.funny.biz.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_search.*
import java.util.ArrayList

class SearchActivity : AppCompatActivity() {
    var gameList : ArrayList<Game> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val game = Game()
        game.title = "abcd"

        gameList.add(game)

        val game2 = Game()
        game2.title = "abcd"

        gameList.add(game2)

        val adapter = GameListAdapter(applicationContext , gameList )
        adapter.notifyDataSetChanged()
        game_listview.adapter = adapter

    }
}