package com.yopachara.catradiod.service

import com.yopachara.catradiod.model.Cat
import com.yopachara.catradiod.model.Model
import retrofit2.Call
import retrofit2.http.GET
import rx.Observable

/**
 * Created by dw03 on 8/17/2016 AD.
 */

interface CatService {
    @GET("now.php")
    fun getSong(): Call<Cat>
}