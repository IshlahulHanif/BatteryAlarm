package com.izura.batteryalarm

interface MainContract {
    interface View {
        fun changeBatteryLevel(level: Int)
        fun startVibrate()
        fun stopVibrate()
    }
    interface Presenter {
        fun onResume()
        fun onPause()
        fun onDestroy()
        fun onBatteryLevelChanged(level: Int)
        fun onPowerConnected ()
        fun onPowerDisconnected ()
        fun onBtnSetAlarmLevelClicked(level: Int)
    }
    interface Interactor {
        var batteryThreshold: Int
        var presenter: Presenter?
        fun bindPowerReceiver()
        fun unBindPowerReceiver()
        fun onPowerConnected ()
        fun onPowerDisconnected ()
        fun getCurrentBattery(): Int
    }
}