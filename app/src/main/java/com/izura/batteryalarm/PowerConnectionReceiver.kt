package com.izura.batteryalarm

import android.widget.Toast
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context


class PowerConnectionReceiver : BroadcastReceiver() {

    private lateinit var onPowerConnected : () -> Unit
    private lateinit var onPowerDisconnected: () -> Unit
    //TODO: add vars

    override fun onReceive(context: Context, intent: Intent) {
        when {
            intent.action == Intent.ACTION_POWER_CONNECTED -> {
                onPowerConnected.invoke()
                Toast.makeText(context, "The device is charging", Toast.LENGTH_SHORT).show()
            }
            intent.action == Intent.ACTION_POWER_DISCONNECTED -> {
                onPowerDisconnected.invoke()
                Toast.makeText(context, "The device is not charging", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "unknown", Toast.LENGTH_SHORT).show()
            }
        }
    }

}