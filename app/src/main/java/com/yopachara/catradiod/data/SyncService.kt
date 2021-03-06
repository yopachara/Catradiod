package com.yopachara.catradiod.data

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.os.IBinder
import com.yopachara.catradiod.CatradiodAPP
import com.yopachara.catradiod.data.DataManager
import com.yopachara.catradiod.data.model.DjSchedule
import com.yopachara.catradiod.extension.isNetworkConnected
import com.yopachara.catradiod.extension.toggleAndroidComponent
import rx.Subscriber

import javax.inject.Inject

import rx.Subscription
import rx.lang.kotlin.FunctionSubscriber
import rx.lang.kotlin.subscriber
import rx.schedulers.Schedulers
import timber.log.Timber

class SyncService : Service() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SyncService::class.java)
        }
    }

    @Inject
    lateinit var dataManager: DataManager

    var subscription: Subscription? = null

    override fun onCreate() {
        super.onCreate()
        (applicationContext as CatradiodAPP)
                .applicationComponent
                .inject(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.i("Starting sync...")

        if (!isNetworkConnected()) {
            Timber.i("Sync canceled, connection not available")
            toggleAndroidComponent(SyncOnConnectionAvailable::class.java, true)
            stopSelf(startId)
            return START_NOT_STICKY
        }

        subscription?.let { if (!it.isUnsubscribed) it.unsubscribe() }

//        subscription = dataManager.syncRibots()
//                .subscribeOn(Schedulers.io())
//                .subscribe(FunctionSubscriber<Ribot>()
//                        .onCompleted {
//                            Timber.i("Synced successfully!")
//                            stopSelf(startId)
//                        }
//                        .onError {
//                            Timber.w(it, "Error syncing.")
//                            stopSelf(startId)
//                        })
        subscription = dataManager.syncSchedule()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(FunctionSubscriber<DjSchedule>()
                        .onError { e ->
                            Timber.e(e)
                            stopSelf(startId)
                        }
                        .onCompleted {
                            Timber.i("onComplete")
//                            Timber.i(dataManager.getPreferenceHelper().getDjSchedule().toString())
                            stopSelf(startId)
                        })

        return START_STICKY
    }

    override fun onDestroy() {
        subscription?.unsubscribe()
        subscription = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    class SyncOnConnectionAvailable : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == CONNECTIVITY_ACTION && context.isNetworkConnected()) {
                Timber.i("Connection is now available, triggering sync...")
                context.toggleAndroidComponent(SyncOnConnectionAvailable::class.java, false)
                context.startService(getStartIntent(context))
            }
        }
    }
}
