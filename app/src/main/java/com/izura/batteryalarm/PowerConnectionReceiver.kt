package com.izura.batteryalarm

import android.widget.Toast
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.os.BatteryManager


class PowerConnectionReceiver : BroadcastReceiver() {

    var onPowerConnected : (() -> Unit)? = null
    var onPowerDisconnected: (() -> Unit)? = null
    var onBatteryLevelChanged: ((level: Int) -> Unit)? = null
    //TODO: add vars

    override fun onReceive(context: Context, intent: Intent) {
        when {
            intent.action == Intent.ACTION_POWER_CONNECTED -> {
                onPowerConnected?.invoke()
                Toast.makeText(context, "The device is charging", Toast.LENGTH_SHORT).show()
            }
            intent.action == Intent.ACTION_POWER_DISCONNECTED -> {
                onPowerDisconnected?.invoke()
                Toast.makeText(context, "The device is not charging", Toast.LENGTH_SHORT).show()
            }
            intent.action == Intent.ACTION_BATTERY_CHANGED -> {
                onBatteryLevelChanged?.invoke(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0))
            }
            else -> {
                Toast.makeText(context, "unknown", Toast.LENGTH_SHORT).show()
            }
        }
    }

}