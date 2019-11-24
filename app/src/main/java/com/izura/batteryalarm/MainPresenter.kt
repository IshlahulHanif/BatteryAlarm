package com.izura.batteryalarm

class MainPresenter(private val view: MainContract.View, private val interactor: MainContract.Interactor)
    : MainContract.Presenter {

    init {
        interactor.presenter = this
    }

    override fun onResume() {
        interactor.bindPowerReceiver()
    }

    override fun onPause() {
//        interactor.unBindPowerReceiver()
    }

    override fun onDestroy() {
        interactor.unBindPowerReceiver()
    }

    override fun onBtnSetAlarmLevelClicked(level: Int) {
        interactor.batteryThreshold = level
    }

    override fun onBatteryLevelChanged(level: Int) {
        view.changeBatteryLevel(level)
        if (interactor.batteryThreshold <= level) {
            view.startVibrate()
        }
    }

    override fun onPowerConnected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPowerDisconnected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}