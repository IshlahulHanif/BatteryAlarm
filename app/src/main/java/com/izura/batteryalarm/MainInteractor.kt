package com.izura.batteryalarm

import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class MainInteractor(private val context: Context) : MainContract.Interactor {
    private val prefs by lazy {context.getDefaultPreferences()}
    override var batteryThreshold: Int
        get() = getSavedBatteryThreshold()
        set(value) {
            info("New threshold saved: $value")
            prefs.setValue("battery_threshold",value)
        }
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

    override fun getCurrentBattery(): Int {
        return powerConnectionReceiver.currentBatteryLvl
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

    private fun getSavedBatteryThreshold() : Int {
        val lvl = prefs.getValue("battery_threshold",0)
        info("Current saved threshold: $lvl")
        return lvl
    }
}