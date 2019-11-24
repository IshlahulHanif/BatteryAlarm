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

    var currentBatteryLvl: Int = 0

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                info("Power connected")
                onPowerConnected?.invoke()
                context.showToast("The device is charging")
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                info("Power disconnected")
                onPowerDisconnected?.invoke()
                context.showToast("The device is not charging")
            }
            Intent.ACTION_BATTERY_CHANGED -> {
                currentBatteryLvl = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                info("Battery level changed detected. Level: $currentBatteryLvl")
                onBatteryLevelChanged?.invoke(currentBatteryLvl)
            }
            else -> {
                warning("Unprocessed action: ${intent.action}")
                Toast.makeText(context, "unknown", Toast.LENGTH_SHORT).show()
            }
        }
    }

}