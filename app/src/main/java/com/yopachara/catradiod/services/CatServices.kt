package com.yopachara.catradiod.services

import com.yopachara.catradiod.models.Model
import okhttp3.ResponseBody
import retrofit2.http.GET
import rx.Observable

/**
 * Created by dw03 on 8/17/2016 AD.
 */

interface CatService {
    @GET("assets/now.php")
    public fun getSong(): Observable<ResponseBody>
}