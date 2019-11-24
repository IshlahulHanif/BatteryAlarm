package com.izura.batteryalarm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import android.os.*
import android.util.Log


class MainActivity : AppCompatActivity(), MainContract.View {
    private val presenter: MainContract.Presenter by lazy { MainPresenter(this,MainInteractor(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //prevent double launch
//        multiLaunchStopper()

        sb_alarm_level.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                et_alarm_level.hint = "$p1"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        //TODO: move onclick listener to presenter
        btn_set_alarm_level.setOnClickListener {
            val level = sb_alarm_level.progress
            presenter.onBtnSetAlarmLevelClicked(level)
            showToast(level.toString())
        }

        sw_alarm_on.setOnCheckedChangeListener { _, isOn ->
            sw_alarm_on.text = if (isOn) "Alarm On" else "Alarm Off"
            presenter.toggleAlarmOn(isOn)
        }

        presenter.onViewCreated()
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

    override fun changeAlarmThreshold(level: Int) {
        sb_alarm_level.progress = level
        et_alarm_level.hint = level.toString()
    }

    override fun changeAlarmOnStatus(isOn: Boolean) {
        info("Alarm switch set to ${if (isOn) "on" else "off"}")
        sw_alarm_on.isChecked = isOn
    }

    private val mVibrateHandler = Handler()
    private val vibrateDuration = 2000L
    private val vibrateDelay = 3000L
    private var isVibrating = false
    override fun startVibrate() {
        //TODO: make stop vibrate/ stop alarm
        if (isVibrating) return
        isVibrating = true
        mVibrateHandler.postDelayed(object: Runnable {
            override fun run() {
                if (!isVibrating) return
                vibrate(vibrateDuration)
                mVibrateHandler.postDelayed(this, vibrateDelay)
            }
        }, vibrateDelay)
    }

    override fun stopVibrate() {
        isVibrating = false
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

//    private fun multiLaunchStopper() {
//        if (!isTaskRoot) {
//            //TODO: send data to tell connection receiver if this is what caused on destroy, soo it will not send log
//            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == intent.action) {
//                warning( "Main Activity is not the root.  Finishing Main Activity instead of launching.")
//                finish()
//                return
//            }
//        }
//    }
}
