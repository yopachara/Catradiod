package com.yopachara.catradiod.ui.main

import com.yopachara.catradiod.data.network.NetworkInteractor
import com.yopachara.catradiod.data.remote.CatApiService
import com.yopachara.catradiod.data.remote.model.Cat
import com.yopachara.catradiod.ui.base.AbstractViewModel
import com.yopachara.catradiod.ui.base.RxViewModel
import io.github.plastix.rxdelay.RxDelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(private val apiService: CatApiService,
                                        private val networkInteractor: NetworkInteractor) : RxViewModel() {

    private var cats: BehaviorSubject<Cat> = BehaviorSubject.create()
    private var networkRequest: Disposable = Disposables.disposed()
    private var loadingState: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    private val fetchErrors: PublishSubject<Throwable> = PublishSubject.create()
    private val networkErrors: PublishSubject<Throwable> = PublishSubject.create()

    fun fetchSong(){
        networkRequest = networkInteractor.hasNetworkConnectionCompletable()
                .andThen(apiService.getSong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen { completed -> completed.delay(1, TimeUnit.MINUTES) }
                .subscribe(
                        { cat ->
                            cats.onNext(cat)
//                            song_detail = cat.now?.song+" "+ cat.now?.name
                            Timber.d(cat.toString())
                        },
                        { error ->
                            Timber.e(error)
                        }
                )
        addDisposable(networkRequest)

    }

    fun getSong(): Observable<Cat> = cats.hide()

    fun fetchErrors(): Observable<Throwable> = fetchErrors.hide()

    fun networkErrors(): Observable<Throwable> = networkErrors.hide()

    fun loadingState(): Observable<Boolean> = loadingState.hide()
}