package com.izura.batteryalarm

interface MainContract {
    interface View {
        fun changeBatteryLevel(level: Int)
        fun changeAlarmThreshold(level: Int)
        fun changeAlarmOnStatus(isOn: Boolean)
        fun startVibrate()
        fun stopVibrate()
    }
    interface Presenter {
        fun onViewCreated()
        fun onResume()
        fun onPause()
        fun onDestroy()
        fun onBatteryLevelChanged(level: Int)
        fun toggleAlarmOn(isOn: Boolean)
        fun onPowerConnected ()
        fun onPowerDisconnected ()
        fun onBtnSetAlarmLevelClicked(level: Int)
    }
    interface Interactor {
        var presenter: Presenter?
        var batteryThreshold: Int
        var isAlarmOn: Boolean
        fun bindPowerReceiver()
        fun unBindPowerReceiver()
        fun onPowerConnected ()
        fun onPowerDisconnected ()
        fun getCurrentBattery(): Int
    }
}