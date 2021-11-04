package io.jitpack.lilya

interface RemoteListener {

    fun onFalseCode(int: Int)

    fun onSuccessCode(offerUrl: String)

    fun onStatusTrue()

    fun onStatusFalse()

    fun nonFirstLaunch(url: String)
}