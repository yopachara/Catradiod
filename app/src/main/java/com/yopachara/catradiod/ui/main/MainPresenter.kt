package com.yopachara.catradiod.ui.main

import com.yopachara.catradiod.data.DataManager
import com.yopachara.catradiod.data.remote.model.Cat
import com.yopachara.catradiod.injection.ConfigPersistent
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

import rx.lang.kotlin.FunctionSubscriber
import rx.lang.kotlin.addTo
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import java.util.concurrent.TimeUnit

@ConfigPersistent
class MainPresenter
@Inject
constructor(private val dataManager: DataManager) : MainContract.Presenter() {

    private val compositeSubscription = CompositeSubscription()

    override fun detachView() {
        super.detachView()
        compositeSubscription.clear()
    }

    //    fun fetchSong(){
    //        networkRequest = networkInteractor.hasNetworkConnectionCompletable()
//                .andThen(apiService.getSong())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .repeatWhen { completed -> completed.delay(1, TimeUnit.MINUTES) }
//                .subscribe(
//                        { cat ->
//                            cats.onNext(cat)
////                            song_detail = cat.now?.song+" "+ cat.now?.name
//                            Timber.d(cat.toString())
//                        },
//                        { error ->
//                            Timber.e(error)
//                        }
//                )
//        addDisposable(networkRequest)
//
//    }
    override fun loadRibots() {
        dataManager.getSong()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .repeatWhen { completed -> completed.delay(1, TimeUnit.MINUTES) }
                .subscribe({
                    cat ->
                    view.showSong(cat)
                    //                            if (it.isEmpty()) view.showRibotsEmpty() else view.showRibots(it)
                }
                        , {
                    Timber.e(it, "There was an error loading the ribots.")
                }
                ).addTo(compositeSubscription)
    }

}