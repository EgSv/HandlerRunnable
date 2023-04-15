package ru.startandroid.develop.handlerrunnable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.PackageManagerCompat.LOG_TAG

class MainActivity : AppCompatActivity() {

    private var pbCount: ProgressBar? = null
    var tvInfo: TextView? = null
    private var chbInfo: CheckBox? = null
    var cnt: Int = 0

    val LOG_TAG = "myLogs"
    private val max = 100

    var h: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        h = Handler()

        pbCount = findViewById<View>(R.id.pbCount) as ProgressBar
        pbCount!!.max = max
        pbCount!!.progress = 0

        tvInfo = findViewById<View>(R.id.tvInfo) as TextView

        chbInfo = findViewById<View>(R.id.chbInfo) as CheckBox

        chbInfo!!.setOnCheckedChangeListener {
            buttonView: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                tvInfo!!.visibility = View.VISIBLE
                h!!.post(showInfo)
            } else {
                tvInfo!!.visibility = View.GONE
                h!!.removeCallbacks(showInfo)
            }
        }

        val t = Thread {
            try {
                while(cnt < max) {
                    Thread.sleep(100)
                    h!!.post(updateProgress)
                    cnt++
                }
            } catch (e:InterruptedException) {
                e.printStackTrace()
            }
        }
        t.start()
    }

    private var updateProgress = Runnable {
        pbCount!!.progress = cnt
    }

    private val showInfo: Runnable = object : Runnable {
        override fun run() {
            Log.d(LOG_TAG, "showInfo")
            tvInfo!!.text = "Count = $cnt"
            h!!.postDelayed(this, 1000)
        }
    }
}