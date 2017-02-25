package com.yopachara.catradiod.data.remote

import com.yopachara.catradiod.data.remote.model.Cat
import com.yopachara.catradiod.data.remote.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface CatService {

    @GET("now.php")
    fun getSong(): Observable<Cat>

}