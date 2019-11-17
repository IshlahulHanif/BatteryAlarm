package com.izura.batteryalarm

class MainPresenter(private val view: MainContract.view, private val interactor: MainContract.interactor) : MainContract.presenter {
    init {
        interactor.presenter = this
    }

    override fun onPowerConnected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPowerDisconnected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}