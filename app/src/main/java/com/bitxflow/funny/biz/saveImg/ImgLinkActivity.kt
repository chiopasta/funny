package com.bitxflow.funny.biz.saveImg

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bitxflow.funny.DB.GameDatabase
import com.bitxflow.funny.DB.ImgLink
import com.bitxflow.funny.R
import com.bitxflow.funny.biz.search.Game
import kotlinx.android.synthetic.main.activity_img_link.*
import java.util.ArrayList

class ImgLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_link)

        val gameDatabase = GameDatabase.getInstance(baseContext)
        val getRunnable = Runnable {
            val links = gameDatabase?.imgLinkDao()?.getImgLink()
            if(links.isNullOrEmpty())
            {
                val imgLink = ImgLink()
                imgLink.name = "wifi_name"
                imgLink.link = "funtime"
                gameDatabase?.imgLinkDao()?.insert(imgLink)

                imgLink.name = "wifi_pw"
                imgLink.link = "123456"
                gameDatabase?.imgLinkDao()?.insert(imgLink)

                val links2 = gameDatabase?.imgLinkDao()?.getImgLink()
                for(imgLink in links2!!)
                {
                    when(imgLink.name)
                    {
                        "wifi_name" -> wifi_name_et.setText(imgLink.link)
                        "wifi_pw" -> wifi_pw_et.setText(imgLink.link)
                    }
                }
            }
            else
            {
                for(imgLink in links)
                {
                    when(imgLink.name)
                    {
                        "wifi_name" -> wifi_name_et.setText(imgLink.link)
                        "wifi_pw" -> wifi_pw_et.setText(imgLink.link)
                    }
                }
            }
        }

        val settingThread = Thread(getRunnable)
        settingThread.start()

        delete_bt.setOnClickListener{

            val deleteRunnable = Runnable {
                gameDatabase?.imgLinkDao()?.deleteAll()
            }

            val settingThread = Thread(deleteRunnable)
            settingThread.start()

            Toast.makeText(applicationContext,"삭제되었습니다",Toast.LENGTH_SHORT).show()

        }


        wifi_name_change_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("wifi_name")
                val link = wifi_name_et.text
                imgLink!!.link = link.toString()
                gameDatabase?.imgLinkDao()?.update(imgLink)
                ChangeComplateTask().execute()
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()

        }

        wifi_pw_change_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("wifi_pw")
                val link = wifi_pw_et.text
                imgLink!!.link = link.toString()
                gameDatabase?.imgLinkDao()?.update(imgLink)
                ChangeComplateTask().execute()
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()

        }

    }


    internal inner class ChangeComplateTask : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String): String {

            return "login"

        }

        override fun onPostExecute(result: String) {
            Toast.makeText(baseContext, "바뀌었습니다", Toast.LENGTH_SHORT).show()
        }
    }


}