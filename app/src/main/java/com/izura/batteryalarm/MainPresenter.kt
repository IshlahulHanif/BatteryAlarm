package com.izura.batteryalarm

class MainPresenter(private val view: MainContract.View, private val interactor: MainContract.Interactor)
    : MainContract.Presenter {

    private var isPowerConnected = false
    private var isAlarmOn : Boolean
        get() = interactor.isAlarmOn
        set(value) { interactor.isAlarmOn = value}

    init {
        interactor.presenter = this
    }

    override fun onViewCreated() {
        view.changeAlarmThreshold(interactor.batteryThreshold)
        interactor.bindPowerReceiver()
        view.changeAlarmOnStatus(isAlarmOn)
    }

    override fun onResume() {
//        toggleAlarmOn(true)
    }

    override fun onPause() {
//        interactor.unBindPowerReceiver()
    }

    override fun onDestroy() {
        //will this cause bug?
        interactor.unBindPowerReceiver()
    }

    override fun onBtnSetAlarmLevelClicked(level: Int) {
        interactor.batteryThreshold = level
    }

    override fun onBatteryLevelChanged(level: Int) {
        view.changeBatteryLevel(level)
        if (interactor.batteryThreshold <= level && isPowerConnected && isAlarmOn) {
            view.startVibrate()
        }
    }

    override fun toggleAlarmOn(isOn: Boolean) {
        isAlarmOn = isOn
        if (!isOn) view.stopVibrate()
        //code bellow is just to prove that if a service registered twice, even after i unregister it
        //it will still receive a broadcast
//        if (isOn) {
//            interactor.bindPowerReceiver()
//        } else {
//            view.stopVibrate()
//            interactor.unBindPowerReceiver()
//        }
    }

    override fun onPowerConnected() {
        isPowerConnected = true
    }

    override fun onPowerDisconnected() {
        isPowerConnected = false
        view.stopVibrate()
    }

}