package com.izura.batteryalarm

interface MainContract {
    interface View {
        fun changeBatteryLevel(level: Int)
    }
    interface Presenter {
        fun onResume()
        fun onPause()
        fun onDestroy()
        fun onBatteryLevelChanged(level: Int)
        fun onPowerConnected ()
        fun onPowerDisconnected ()
    }
    interface Interactor {
        var presenter: Presenter?
        fun bindPowerReceiver()
        fun unBindPowerReceiver()
        fun onPowerConnected ()
        fun onPowerDisconnected ()
    }
}