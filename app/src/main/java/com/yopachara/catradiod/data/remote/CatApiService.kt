package com.yopachara.catradiod.data.remote

import com.yopachara.catradiod.data.remote.model.Cat
import com.yopachara.catradiod.data.remote.model.SearchResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {

    @GET("now.php")
    fun getSong(): Observable<Cat>

}