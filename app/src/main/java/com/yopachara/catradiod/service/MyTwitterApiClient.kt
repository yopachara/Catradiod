package com.yopachara.catradiod.service

import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by dw03 on 8/23/2016 AD.
 */


interface MyTwitterApiClient {
    @GET("/1.1/users/show.json")
    fun show(@Query("user_id") id: Long)
            : Call<User>
}
