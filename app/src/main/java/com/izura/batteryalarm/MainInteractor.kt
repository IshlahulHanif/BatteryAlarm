package com.izura.batteryalarm

import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class MainInteractor(private val context: Context) : MainContract.Interactor {
    override var presenter: MainContract.Presenter? = null
    lateinit var powerConnectionReceiver : PowerConnectionReceiver

    override fun bindPowerReceiver() {
        powerConnectionReceiver = PowerConnectionReceiver()
        presenter?.let {
            powerConnectionReceiver.onBatteryLevelChanged = it::onBatteryLevelChanged
            powerConnectionReceiver.onPowerConnected = it::onPowerConnected
            powerConnectionReceiver.onPowerDisconnected = it::onPowerDisconnected
        }

        val ifilter = IntentFilter()
        ifilter.addAction(Intent.ACTION_POWER_CONNECTED)
        ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        ifilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(powerConnectionReceiver, ifilter)
    }

    override fun unBindPowerReceiver() {
        context.unregisterReceiver(powerConnectionReceiver)
    }

    override fun onPowerConnected() {
        presenter!!.onPowerConnected()
    }

    override fun onPowerDisconnected() {
        presenter!!.onPowerDisconnected()
    }
}