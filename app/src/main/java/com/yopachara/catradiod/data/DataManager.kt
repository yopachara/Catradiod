package com.yopachara.catradiod.data

import com.yopachara.catradiod.data.local.PreferencesHelper
import com.yopachara.catradiod.data.model.DjSchedule
import com.yopachara.catradiod.data.remote.CatService
import com.yopachara.catradiod.data.remote.CatWebService
import com.yopachara.catradiod.data.remote.model.Cat
import javax.inject.Inject
import javax.inject.Singleton

import rx.Observable
import timber.log.Timber

@Singleton
class DataManager
@Inject constructor(private val catService: CatService, private val catWebService: CatWebService, private val preferencesHelper: PreferencesHelper) {

    //    fun syncRibots(): Observable<Ribot> {
//        return ribotsService.getRibots()
//                .concatMap { databaseHelper.setRibots(it) }
//    }
//
//
    fun getPreferenceHelper(): PreferencesHelper {
        return preferencesHelper
    }

    fun syncSchedule(): Observable<DjSchedule> {
        return catWebService.getDj()
                .concatMap {
//                    Timber.d("syncSchedule "+it.toString())
                    preferencesHelper.saveDjSchedules(it)
                }
    }

    fun getSchedule(): Observable<DjSchedule> {
        return catWebService.getDj().distinct()
    }
    fun getSong(): Observable<Cat> {
        return catService.getSong().distinct()
    }
}
