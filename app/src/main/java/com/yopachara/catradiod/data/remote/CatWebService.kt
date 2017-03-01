package com.yopachara.catradiod.data.remote

import com.yopachara.catradiod.data.model.DjSchedule
import retrofit2.http.GET
import rx.Observable

interface CatWebService {

    @GET("service/web_service/dj")
    fun getDj(): Observable<DjSchedule>

}