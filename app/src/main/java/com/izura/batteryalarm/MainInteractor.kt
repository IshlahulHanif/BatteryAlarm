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
            prefs.setValue(BATTERY_THRESHOLD,value)
        }
    override var isAlarmOn: Boolean
        get() = prefs.getBoolean(ALARM_ON_STATUS,true)
        set(value) {
            info("Alarm set to ${if (value) "on" else "off"}")
            prefs.setValue(ALARM_ON_STATUS,value)
        }
    override var presenter: MainContract.Presenter? = null
    lateinit var powerConnectionReceiver : PowerConnectionReceiver

    override fun bindPowerReceiver() {
        info("Power Receiver bounded")
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
        try {
            info("Power Receiver unbounded")
            context.unregisterReceiver(powerConnectionReceiver)
        } catch (e: IllegalArgumentException) {
            warning("Power Receiver has not been bounded")
            warning(e.localizedMessage)
        }
    }

    override fun onPowerConnected() {
        presenter!!.onPowerConnected()
    }

    override fun onPowerDisconnected() {
        presenter!!.onPowerDisconnected()
    }

    private fun getSavedBatteryThreshold() : Int {
        val lvl = prefs.getValue(BATTERY_THRESHOLD,0)
        info("Current saved threshold: $lvl")
        return lvl
    }
}