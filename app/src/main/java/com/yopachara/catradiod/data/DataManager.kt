package com.yopachara.catradiod.data

import com.yopachara.catradiod.data.remote.CatService
import com.yopachara.catradiod.data.remote.model.Cat
import javax.inject.Inject
import javax.inject.Singleton

import rx.Observable

@Singleton
class DataManager @Inject constructor(private val catService: CatService) {

//    fun syncRibots(): Observable<Ribot> {
//        return ribotsService.getRibots()
//                .concatMap { databaseHelper.setRibots(it) }
//    }
//
//

   fun getSong(): Observable<Cat> {
        return catService.getSong().distinct()
    }
}
