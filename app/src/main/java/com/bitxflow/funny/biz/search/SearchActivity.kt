package com.bitxflow.funny.biz.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_search.*
import java.util.ArrayList

class SearchActivity : AppCompatActivity() {
    var gameList : ArrayList<Game> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var ppl = 0;

        val game = Game()
        game.title = "abcd"

        gameList.add(game)

        val game2 = Game()
        game2.title = "efg"

        gameList.add(game2)

        val adapter = GameListAdapter(applicationContext , gameList )
        adapter.notifyDataSetChanged()
        game_listview.adapter = adapter



        search_bt.setOnClickListener {
//            val id = ppl_rg.checkedRadioButtonId
//            val radioButton = (RadioButton)findViewById(id)
            when(ppl_rg.checkedRadioButtonId){
                R.id.ppl2_radio_bt -> ppl=2
                R.id.ppl3_radio_bt -> ppl=3
                R.id.ppl4_radio_bt -> ppl=4
            }

            Toast.makeText(applicationContext,"ppl?:" + ppl,Toast.LENGTH_SHORT).show()
        }

    }
}