package com.bitxflow.funny.biz.search

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bitxflow.funny.R
import com.bitxflow.funny.send.SendServer
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.search_header.*
import org.json.JSONObject
import java.util.ArrayList

class SearchActivity : AppCompatActivity() {
    var gameList : ArrayList<Game> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var ppl = 0;
        var level = ""
        var type = ""

//        val game = Game()
//        game.title = "할리갈리"
//        val str = "순발력,퍼즐"
//        game.type = str.split(",")
//        game.img_url = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxOTEyMDNfMjA4%2FMDAxNTc1MzUyNDU0NjU2.5baBn7OpRGonQIUUlrpjVOusF4g4UAek4j_PqhZ2JGcg.Wvnz79SMBX51Hhq_eiR_6hvlDT1zCQpP2SUj29cjP2Mg.JPEG.qnsegtgk%2F%2525B7%2525E7%2525B9%2525CC%2525C5%2525A5%2525BA%2525EA%252B%2525C6%2525AE%2525B7%2525A1%2525BA%2525ED.jpg&type=sc960_832"
//        val numbers = intArrayOf(2,3,4,5,6)
//        game.people = numbers
//        game.level = "쉬움";
//        gameList.add(game)
//
//        val game2 = Game()
//        game2.title = "블러프"
//        val str2 = "추리,구라,순발력"
//        game2.type = str2.split(",")
//
//        val numbers2 = intArrayOf(3,4,5,6)
//        game2.people = numbers2
//        game2.level = "중간";
//        gameList.add(game2)
//
//
//        val game3 = Game()
//        game3.title = "뱅"
//        val str3 = "추리,구라"
//        game3.type = str3.split(",")
//
//        val numbers3 = intArrayOf(6,7,8,9)
//        game3.people = numbers3
//        game3.level = "어려움";
//        gameList.add(game3)
//
//        val game4 = Game()
//        game4.title = "루미큐브"
//        val str4 = "추리,구라,퍼즐,심리"
//        game4.level = "중간";
//        game4.type = str4.split(",")
//
//        val numbers4 = intArrayOf(2,3,4,5)
//        game4.people = numbers4
////        gameList.add(game4)

        val header: View =
            layoutInflater.inflate(R.layout.search_header, null, false)

        var baseArrayList : ArrayList<String> = ArrayList()
        baseArrayList.add("검색해주세요")
        val adapter = ArrayAdapter(applicationContext , android.R.layout.simple_list_item_1,baseArrayList)
        adapter.notifyDataSetChanged()
        game_listview.adapter = adapter
        game_listview.addHeaderView(header)

        search_bt.setOnClickListener {

            val search_title = search_et.text.toString()

            var search_gameList : List<Game>

            ///////////////////// 조건 검색
            if(search_title.equals(""))
            {

                when(ppl_rg.checkedRadioButtonId){
                    R.id.ppl2_radio_bt -> ppl=2
                    R.id.ppl3_radio_bt -> ppl=3
                    R.id.ppl4_radio_bt -> ppl=4
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
                    Log.d("bitx_log","level in")
                }
                if(!type.isNullOrBlank()) {
                    search_gameList = search_gameList.filter { it.type.contains(type) }
                    Log.d("bitx_log","type in")
                }
                var searchedList : ArrayList<Game> = ArrayList()
                searchedList.addAll(search_gameList)
                val adapter = GameListAdapter(applicationContext , searchedList)
                adapter.notifyDataSetChanged()
                game_listview.adapter = adapter
            }
            else //////////////이름검색
            {
                search_gameList = gameList.filter{it.name!!.contains(search_title)}
                var searchedList : ArrayList<Game> = ArrayList()
                searchedList.addAll(search_gameList)
                val adapter = GameListAdapter(applicationContext , searchedList)
                adapter.notifyDataSetChanged()
                game_listview.adapter = adapter

            }


        }

        testTask().execute()

    }

    internal inner class getGameListTask : AsyncTask<String, String, String>() {
        var userId : String =""

        override fun doInBackground(vararg params: String): String {
            val su = SendServer()
            userId = params[0].trim()

            val url = "games"
//            val postDataParams = JSONObject()
//            postDataParams.put("userid", userId.toUpperCase())

            return su.requestGET(url)

        }

        override fun onPostExecute(result: String) {
//            Log.d("bitx_log","result: $result")
            if(result =="")
            {
                Toast.makeText(baseContext, "서버 통신오류, 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                val `object` = JSONObject(result)
                val count = `object`.getInt("totalCount")
                val entities = `object`.getJSONArray("entities")
                if (count == 0) {

                }
                else
                {
                    gameList.clear()
                    for(i in 0 until count)
                    {
                        val json = entities.getJSONObject(i)
                        val gameID = json.getString("gameID")
                        val name = json.getString("name")
                        val engName = json.getString("engName")
                        val gameTime = json.getString("gameTime")
                        val expTime = json.getString("expTime")
                        val expText = json.getString("expText")
                        val expImg = json.getString("expImg")
                        val expUrl = json.getString("expUrl")
                        val type = json.getString("type")
                        val level = json.getString("level")
                        val people = json.getString("people")
                        val recommend = json.getString("recommend")
                        val hit = json.getInt("hit")
                        val memo = json.getString("memo")

                        val game = Game()
                        game.gameID = gameID
                        game.name = name
                        game.engName = engName
                        game.gameTime = gameTime
                        game.expTime = expTime
                        game.expText = expText
                        game.expUrl = expUrl
                        game.expImg = expImg
                        game.type = type.split(",")
                        game.level = level
                        game.recommend = recommend
                        game.hit = hit
                        game.memo = memo

                        val strPpl = people.split(",")
                        var numbers = ArrayList<Int>()
                        for(j in 0 until strPpl.size)
                        {
                            numbers.add(Integer.parseInt(strPpl[j]))
                        }
                        game.people = numbers.toIntArray()
                        gameList.add(game)
                    }
                }
            }
        }
    }


    internal inner class testTask : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String): String {
            val su = SendServer()

            val url = "games"
//            val postDataParams = JSONObject()
//            postDataParams.put("userid", userId.toUpperCase())

            return su.requestGET(url)

        }

        override fun onPostExecute(result: String) {
            val game = Game()
            game.name = "할리갈리"
            val str = "순발력,퍼즐"
            game.type = str.split(",")
            game.expImg = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAxOTEyMDNfMjA4%2FMDAxNTc1MzUyNDU0NjU2.5baBn7OpRGonQIUUlrpjVOusF4g4UAek4j_PqhZ2JGcg.Wvnz79SMBX51Hhq_eiR_6hvlDT1zCQpP2SUj29cjP2Mg.JPEG.qnsegtgk%2F%2525B7%2525E7%2525B9%2525CC%2525C5%2525A5%2525BA%2525EA%252B%2525C6%2525AE%2525B7%2525A1%2525BA%2525ED.jpg&type=sc960_832"
            val numbers = intArrayOf(2,3,4,5,6)
            game.people = numbers
            game.level = "쉬움";
            game.expUrl="https://youtu.be/5VkE9fEj7sM"
            gameList.add(game)

            val game2 = Game()
            game2.name = "블러프"
            val str2 = "추리,구라,순발력"
            game2.type = str2.split(",")

            val numbers2 = intArrayOf(3,4,5,6)
            game2.people = numbers2
            game2.level = "중간";
            gameList.add(game2)


            val game3 = Game()
            game3.name = "뱅"
            val str3 = "추리,구라"
            game3.type = str3.split(",")

            val numbers3 = intArrayOf(6,7,8,9)
            game3.people = numbers3
            game3.level = "어려움";
            gameList.add(game3)

            val game4 = Game()
            game4.name = "루미큐브"
            val str4 = "추리,구라,퍼즐,심리"
            game4.level = "중간";
            game4.type = str4.split(",")

            val numbers4 = intArrayOf(2,3,4,5)
            game4.people = numbers4
            gameList.add(game4)

        }
    }

}