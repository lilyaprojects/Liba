package io.jitpack.api.apps

import android.app.Activity
import android.content.Context
import io.jitpack.api.utils.Constants.appsDevKey
import io.jitpack.api.firebase.FirebaseRemoteConfig
import io.jitpack.api.onesignsl.OneSignal
import io.jitpack.api.utils.Constants
import io.jitpack.api.Repository
import io.jitpack.api.bd.LinkDatabase
import io.jitpack.api.Storage

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