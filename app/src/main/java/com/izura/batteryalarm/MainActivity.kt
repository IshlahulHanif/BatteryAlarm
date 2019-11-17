package com.izura.batteryalarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainContract.view {

    private lateinit var powerConnectionReceiver : PowerConnectionReceiver
    private val presenter: MainContract.presenter = MainPresenter(this,MainInteractor(this))

//    private val mBatInfoReceiver = object : BroadcastReceiver() {
//        override fun onReceive(ctxt: Context, intent: Intent) {
//            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
//            tv_battery_state!!.text = "$level%"
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        powerConnectionReceiver = PowerConnectionReceiver()

        val ifilter = IntentFilter()
        ifilter.addAction(Intent.ACTION_POWER_CONNECTED)
        ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        registerReceiver(powerConnectionReceiver, ifilter)


        val batteryStatus: Intent? = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level : Float? = batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level / scale.toFloat()
        }
        tv_battery_state.text = "$level"
//        registerReceiver(mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(powerConnectionReceiver)
//        unregisterReceiver(mBatInfoReceiver)
    }
}
