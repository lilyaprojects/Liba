package sdk.library.lilya.apps

import android.app.Activity
import android.content.Context
import sdk.library.lilya.utils.Constants.appsDevKey
import sdk.library.lilya.firebase.FirebaseRemoteConfig
import sdk.library.lilya.onesignsl.OneSignal
import sdk.library.lilya.utils.Constants
import sdk.library.lilya.Repository
import sdk.library.lilya.bd.LinkDatabase
import sdk.library.lilya.Storage

object AppsProjector {


    lateinit var preferences: Storage.Preferences
    var repository: Repository? = null



    fun createRemoteConfigInstance(activity: Activity): FirebaseRemoteConfig {
        preferences = Storage.Preferences(activity, Constants.NAME,
            Constants.MAINKEY,
            Constants.CHYPRBOOL
        )
        return FirebaseRemoteConfig(activity)
    }

    fun createAppsInstance(context: Context, devKey: String): AppsflyerManager {
        appsDevKey = devKey
       return AppsflyerManager(context, devKey)
    }

    fun createOneSignalInstance(context: Context, oneSignalId: String): OneSignal {
        return OneSignal(context, oneSignalId)
    }

    fun createRepoInstance(context: Context): Repository {
        if (repository == null){
            return Repository(LinkDatabase.getDatabase(context).linkDao())
        } else {
            return repository as Repository
        }
    }

}