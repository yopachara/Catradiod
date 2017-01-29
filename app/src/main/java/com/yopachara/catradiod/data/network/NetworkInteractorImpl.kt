package com.yopachara.catradiod.data.network

import android.net.ConnectivityManager
import com.yopachara.catradiod.data.network.NetworkInteractor
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkInteractorImpl @Inject constructor(private val connectivityManager:
                                                ConnectivityManager) : NetworkInteractor {

    override fun hasNetworkConnection(): Boolean = connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false

    override fun hasNetworkConnectionCompletable(): Completable {
        if (hasNetworkConnection()) {
            return Completable.complete()
        } else {
            return Completable.error { NetworkInteractor.NetworkUnavailableException() }
        }
    }

}