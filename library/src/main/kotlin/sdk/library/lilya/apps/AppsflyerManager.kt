package sdk.library.lilya.apps

import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import sdk.library.lilya.RemoteListener
import sdk.library.lilya.apps.AppsProjector.preferences
import sdk.library.lilya.utils.Constants
import sdk.library.lilya.utils.Constants.LOG
import sdk.library.lilya.utils.Constants.ONCONVERSION
import sdk.library.lilya.utils.Constants.TAG
import sdk.library.lilya.utils.Constants.TRUE
import sdk.library.lilya.utils.Link
import sdk.library.lilya.utils.Utils


class AppsflyerManager(private val context: Context, private val appsDevKey: String):
    RemoteListener {

   private lateinit var appsflyerListener: AppsflyerListener

    fun start(offerUrl: String) {

        appsflyerListener = context as AppsflyerListener

        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                data?.let { cvData ->
                    cvData.map {
                        if (LOG) Log.d(TAG, "got apps Data - succes conversion")
                        when (preferences.getOnConversionDataSuccess(ONCONVERSION)) {
                            "null" -> {
                                preferences.setOnConversionDataSuccess(
                                    ONCONVERSION,
                                    TRUE
                                )
                                if (LOG) Log.d(TAG, "got apps Data - $data")
                                if (data["campaign"].toString().contains("sub")) {

                                    val url = Utils.getFinalUrl(
                                        offerUrl,
                                        data["campaign"].toString(),
                                        context, data["af_c_id"].toString(),
                                        data["media_source"].toString(),
                                        data["advertising_id"].toString()
                                    )


                                    if (LOG) Log.d(TAG, "$url -- final url")
                                    AppsProjector.createRepoInstance(context).insert(Link(1, url))
                                    appsflyerListener.onConversionDataSuccess(data, url)

                                } else {
                                    preferences.setOnConversionDataSuccess(
                                        ONCONVERSION,
                                        Constants.TRUE
                                    )
                                    val url = offerUrl + "?app_id=" + Utils.getAppBundle(context) +
                                            "&af_status=" + "Organic" +
                                            "&afToken=" + appsDevKey +
                                            "&afid=" + AppsFlyerLib.getInstance().getAppsFlyerUID(context)
                                    AppsProjector.createRepoInstance(context).insert(Link(1, url))
                                    appsflyerListener.onConversionDataSuccess(data, url)
                                }
                            }
                            "true" -> {

                            }
                            "false" -> {

                            }
                            else -> {

                            }
                        }


                    }
                }
            }

            override fun onConversionDataFail(error: String?) {
                if (LOG) Log.d(TAG, "onConversionDataFail")
                appsflyerListener.onConversionDataFail(error)
            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                data?.map {
                    if (LOG) Log.d(TAG, "onAppOpenAttribution")
                }
            }

            override fun onAttributionFailure(error: String?) {
                if (LOG) Log.d(TAG, "onAttributionFailure")
            }
        }
        AppsFlyerLib.getInstance().init(appsDevKey, conversionDataListener, context)
        AppsFlyerLib.getInstance().start(context)
    }

    override fun onFalseCode(int: Int) {

    }

    override fun onSuccessCode(offerUrl: String) {
        Log.d(TAG, "onSuccessCode AppsFlyer Class")
        start(offerUrl)
    }

    override fun onStatusTrue() {

    }

    override fun onStatusFalse() {

    }

    override fun nonFirstLaunch(url: String) {

    }

}