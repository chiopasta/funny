package com.bitxflow.funny.biz.saveImg

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bitxflow.funny.DB.GameDatabase
import com.bitxflow.funny.DB.ImgLink
import com.bitxflow.funny.R
import kotlinx.android.synthetic.main.activity_img_link.*

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
                imgLink.name = "intro0"
                imgLink.link = "http://naver.me/x1e9yTAN"
                gameDatabase?.imgLinkDao()?.insert(imgLink)

                imgLink.name = "intro1"
                imgLink.link = "http://naver.me/x1e9yTAN"
                gameDatabase?.imgLinkDao()?.insert(imgLink)

                imgLink.name = "intro2"
                imgLink.link = "http://naver.me/x1e9yTAN"
                gameDatabase?.imgLinkDao()?.insert(imgLink)

                imgLink.name = "fee"
                imgLink.link = "http://naver.me/x1e9yTAN"
                gameDatabase?.imgLinkDao()?.insert(imgLink)

                imgLink.name = "beginner"
                imgLink.link = "http://naver.me/x1e9yTAN"
                gameDatabase?.imgLinkDao()?.insert(imgLink)

                imgLink.name = "snack0"
                imgLink.link = "http://naver.me/x1e9yTAN"
                gameDatabase?.imgLinkDao()?.insert(imgLink)

                imgLink.name = "snack1"
                imgLink.link = "http://naver.me/x1e9yTAN"
                gameDatabase?.imgLinkDao()?.insert(imgLink)

                imgLink.name = "snack2"
                imgLink.link = "http://naver.me/x1e9yTAN"
                gameDatabase?.imgLinkDao()?.insert(imgLink)

                imgLink.name = "snack3"
                imgLink.link = "http://naver.me/x1e9yTAN"
                gameDatabase?.imgLinkDao()?.insert(imgLink)

                imgLink.name = "apk"
                imgLink.link = "http://naver.me/x1e9yTAN"
                gameDatabase?.imgLinkDao()?.insert(imgLink)

            }
            else
            {
                for(imgLink in links)
                {
                    when(imgLink.name)
                    {
                        "intro0" -> intro0_et.setText(imgLink.link)
                        "intro1" -> intro1_et.setText(imgLink.link)
                        "intro2" -> intro2_et.setText(imgLink.link)
                        "fee" -> fee_et.setText(imgLink.link)
                        "beginner" -> beginner_et.setText(imgLink.link)
                        "snack0" -> snack0_et.setText(imgLink.link)
                        "snack1" -> snack1_et.setText(imgLink.link)
                        "snack2" -> snack2_et.setText(imgLink.link)
                        "snack3" -> snack3_et.setText(imgLink.link)
                        "apk" -> apk_et.setText(imgLink.link)
                    }
                }
            }
        }

        val settingThread = Thread(getRunnable)
        settingThread.start()

        intro0_change_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("intro0")
                val link = intro0_et.text
                imgLink!!.link = link.toString()
                gameDatabase?.imgLinkDao()?.update(imgLink)
                ChangeComplateTask().execute()
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()

        }

        intro0_get_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("intro0")
                getTask().execute(imgLink!!.link)
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()
        }

        intro1_change_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("intro1")
                val link = intro1_et.text
                imgLink!!.link = link.toString()
                gameDatabase?.imgLinkDao()?.update(imgLink)
                ChangeComplateTask().execute()
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()

        }

        intro1_get_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("intro1")
                getTask().execute(imgLink!!.link)
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()
        }

        intro2_change_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("intro2")
                val link = intro2_et.text
                imgLink!!.link = link.toString()
                gameDatabase?.imgLinkDao()?.update(imgLink)
                ChangeComplateTask().execute()
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()

        }

        intro2_get_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("intro2")
                getTask().execute(imgLink!!.link)
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()
        }

        fee_change_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("fee")
                val link = fee_et.text
                imgLink!!.link = link.toString()
                gameDatabase?.imgLinkDao()?.update(imgLink)
                ChangeComplateTask().execute()
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()

        }

        fee_get_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("fee")
                getTask().execute(imgLink!!.link)
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()
        }

        beginner_change_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("beginner")
                val link = beginner_et.text
                imgLink!!.link = link.toString()
                gameDatabase?.imgLinkDao()?.update(imgLink)
                ChangeComplateTask().execute()
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()

        }

        beginner_get_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("beginner")
                getTask().execute(imgLink!!.link)
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()
        }

        snack0_change_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("snack0")
                val link = snack0_et.text
                imgLink!!.link = link.toString()
                gameDatabase?.imgLinkDao()?.update(imgLink)
                ChangeComplateTask().execute()
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()

        }

        snack0_get_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("snack0")
                getTask().execute(imgLink!!.link)
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()
        }

        snack1_change_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("snack1")
                val link = snack1_et.text
                imgLink!!.link = link.toString()
                gameDatabase?.imgLinkDao()?.update(imgLink)
                ChangeComplateTask().execute()
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()

        }

        snack1_get_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("snack1")
                getTask().execute(imgLink!!.link)
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()
        }


        snack2_change_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("snack2")
                val link = snack2_et.text
                imgLink!!.link = link.toString()
                gameDatabase?.imgLinkDao()?.update(imgLink)
                ChangeComplateTask().execute()
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()

        }

        snack2_get_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("snack2")
                getTask().execute(imgLink!!.link)
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()
        }

        snack3_change_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("snack3")
                val link = snack3_et.text
                imgLink!!.link = link.toString()
                gameDatabase?.imgLinkDao()?.update(imgLink)
                ChangeComplateTask().execute()
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()

        }

        snack3_get_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("snack3")
                getTask().execute(imgLink!!.link)
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()
        }

        apk_change_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("apk")
                val link = apk_et.text
                imgLink!!.link = link.toString()
                gameDatabase?.imgLinkDao()?.update(imgLink)
                ChangeComplateTask().execute()
            }
            val settingThread = Thread(changeRunnable)
            settingThread.start()

        }

        apk_get_bt.setOnClickListener {
            val changeRunnable = Runnable {
                val imgLink = gameDatabase?.imgLinkDao()?.getImgLink("apk")
                getTask().execute(imgLink!!.link)
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

    internal inner class getTask : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String): String {

            return params[0]

        }

        override fun onPostExecute(result: String) {
            link_wv.loadUrl(result)
        }
    }

}