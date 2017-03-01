package com.yopachara.catradiod.data

import com.yopachara.catradiod.data.model.DjSchedule
import com.yopachara.catradiod.data.remote.CatService
import com.yopachara.catradiod.data.remote.CatWebService
import com.yopachara.catradiod.data.remote.model.Cat
import javax.inject.Inject
import javax.inject.Singleton

import rx.Observable

@Singleton
class DataManager
@Inject constructor(private val catService: CatService, private val catWebService: CatWebService) {

    //    fun syncRibots(): Observable<Ribot> {
//        return ribotsService.getRibots()
//                .concatMap { databaseHelper.setRibots(it) }
//    }
//
//
    fun syncSchedule(): Observable<DjSchedule> {
        return catWebService.getDj()
    }

    fun getSong(): Observable<Cat> {
        return catService.getSong().distinct()
    }
}
