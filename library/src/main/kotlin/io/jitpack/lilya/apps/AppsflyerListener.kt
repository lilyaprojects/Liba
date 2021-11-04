package io.jitpack.lilya.apps

interface AppsflyerListener {

    fun onConversionDataSuccess(data: MutableMap<String, Any>?, url: String)

    fun onConversionDataFail(error: String?)

}