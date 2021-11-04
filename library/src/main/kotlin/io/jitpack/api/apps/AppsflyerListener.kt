package io.jitpack.api.apps

interface AppsflyerListener {

    fun onConversionDataSuccess(data: MutableMap<String, Any>?, url: String)

    fun onConversionDataFail(error: String?)

}