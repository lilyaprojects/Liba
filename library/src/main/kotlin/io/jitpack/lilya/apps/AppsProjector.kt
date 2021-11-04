package io.jitpack.lilya.apps

import android.app.Activity
import android.content.Context
import io.jitpack.lilya.utils.Constants.appsDevKey
import io.jitpack.lilya.firebase.FirebaseRemoteConfig
import io.jitpack.lilya.onesignsl.OneSignal
import io.jitpack.lilya.utils.Constants
import io.jitpack.lilya.Repository
import io.jitpack.lilya.bd.LinkDatabase
import io.jitpack.lilya.Storage

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