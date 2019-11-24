package com.izura.batteryalarm

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context.VIBRATOR_SERVICE
import android.content.SharedPreferences
import android.os.*
import androidx.core.content.ContextCompat.getSystemService
import android.widget.TextView
import android.os.AsyncTask
import androidx.core.os.postDelayed


class MainActivity : AppCompatActivity(), MainContract.View {
    private val presenter: MainContract.Presenter by lazy { MainPresenter(this,MainInteractor(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sb_alarm_level.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                et_alarm_level.hint = "$p1"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        btn_set_alarm_level.setOnClickListener {
            val level = sb_alarm_level.progress
            presenter.onBtnSetAlarmLevelClicked(level)
            showToast(level.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun changeBatteryLevel(level: Int) {
        tv_battery_state.text = "$level"
    }
    private val mVibrateHandler = Handler()
    private val vibrateDuration = 2000L
    private val vibrateDelay = 3000L
    override fun startVibrate() {
        //TODO: dont revibrate if its alr vibrating
        //TODO: make stop vibrate/ stop alarm
        mVibrateHandler.postDelayed(object: Runnable {
            override fun run() {
                vibrate(vibrateDuration)
                mVibrateHandler.postDelayed(this, vibrateDelay)
            }
        }, vibrateDelay)
    }

    override fun stopVibrate() {
        mVibrateHandler.removeCallbacksAndMessages(null)
    }

    private fun vibrate(duration: Long) {
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            @Suppress("DEPRECATION")
            v.vibrate(duration)
        }
    }
}
