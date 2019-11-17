package com.izura.batteryalarm

interface MainContract {
    interface view {

    }
    interface presenter {
        fun onPowerConnected ()
        fun onPowerDisconnected ()
    }
    interface interactor {
        var presenter: presenter?
        fun bindReceiver()
        fun onPowerConnected ()
        fun onPowerDisconnected ()
    }
}