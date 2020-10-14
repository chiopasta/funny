package com.bitxflow.funny.biz.search

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.bitxflow.funny.R
import com.bitxflow.funny.send.SendServer
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_search.*
import org.json.JSONObject
import java.util.ArrayList

class SearchActivity : AppCompatActivity() {
    var gameList : ArrayList<Game> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var ppl = 0;

        val game = Game()
        game.title = "abcd"
        game.type = "추리"
        game.img_url = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxOTEyMDNfMjA4%2FMDAxNTc1MzUyNDU0NjU2.5baBn7OpRGonQIUUlrpjVOusF4g4UAek4j_PqhZ2JGcg.Wvnz79SMBX51Hhq_eiR_6hvlDT1zCQpP2SUj29cjP2Mg.JPEG.qnsegtgk%2F%2525B7%2525E7%2525B9%2525CC%2525C5%2525A5%2525BA%2525EA%252B%2525C6%2525AE%2525B7%2525A1%2525BA%2525ED.jpg&type=sc960_832"
        val numbers = intArrayOf(2,3,4)
        game.people = numbers
        gameList.add(game)

        val game2 = Game()
        game2.title = "efg"
        game2.type = "순발력"
        val numbers2 = intArrayOf(3,4,5,6)
        game2.people = numbers2
        gameList.add(game2)


        val adapter = GameListAdapter(applicationContext , gameList )
        adapter.notifyDataSetChanged()
        game_listview.adapter = adapter


        search_bt.setOnClickListener {
//            val id = ppl_rg.checkedRadioButtonId
//            val radioButton = (RadioButton)findViewById(id)
            search_box_tv.text = ""

            val search_title = search_et.text.toString()
            Log.d("bitx_log","title:" + search_title)

            var gameList2 : ArrayList<Game> = ArrayList()
            ///////////////////// 조건 검색
            if(search_title.equals(""))
            {

                //타입검색
//              gameList2.addAll(gameList.filter{it.type == "순발력"})

                gameList2.addAll(gameList.filter{it.people.contains(2)})


                val adapter = GameListAdapter(applicationContext , gameList2 )
                adapter.notifyDataSetChanged()
                game_listview.adapter = adapter

                when(ppl_rg.checkedRadioButtonId){
                    R.id.ppl2_radio_bt -> ppl=2
                    R.id.ppl3_radio_bt -> ppl=3
                    R.id.ppl4_radio_bt -> ppl=4
                }

                Toast.makeText(applicationContext,"ppl?:" + ppl,Toast.LENGTH_SHORT).show()
            }
            else //////////////이름검색
            {
                gameList2.addAll(gameList.filter{it.title!!.contains(search_title)})

                val adapter = GameListAdapter(applicationContext , gameList2 )
                adapter.notifyDataSetChanged()
                game_listview.adapter = adapter


            }



        }

    }

    internal inner class getGameListTask : AsyncTask<String, String, String>() {
        var userId : String =""

        override fun doInBackground(vararg params: String): String {
            val su = SendServer()
            userId = params[0].trim()

            val url = "login"
            val postDataParams = JSONObject()
            postDataParams.put("userid", userId.toUpperCase())

            return su.requestPOST(url,postDataParams)

        }

        override fun onPostExecute(result: String) {
//            Log.d("bitx_log","result: $result")
            if(result =="")
            {
                Toast.makeText(baseContext, "서버 통신오류, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                val json = JSONObject(result)
                val success = json.getBoolean("success")
                setResult(Activity.RESULT_OK, Intent().putExtra("BackPress", userId))
                finish()
            }
        }
    }

}