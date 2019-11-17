package com.izura.batteryalarm

import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class MainInteractor(private val context: Context) : MainContract.interactor {
    override var presenter: MainContract.presenter? = null
    lateinit var powerConnectionReceiver : PowerConnectionReceiver

    override fun bindReceiver() {
        powerConnectionReceiver = PowerConnectionReceiver(::onPowerConnected,::onPowerDisconnected)
        val ifilter = IntentFilter()
        ifilter.addAction(Intent.ACTION_POWER_CONNECTED)
        ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        context.registerReceiver(powerConnectionReceiver, ifilter)
    }

    override fun onPowerConnected() {
        presenter!!.onPowerConnected()
    }

    override fun onPowerDisconnected() {
        presenter!!.onPowerDisconnected()
    }
}