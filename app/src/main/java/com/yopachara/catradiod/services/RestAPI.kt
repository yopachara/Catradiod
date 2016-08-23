package com.yopachara.catradiod.services

import com.twitter.sdk.android.core.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
/**
 * Created by dw03 on 8/23/2016 AD.
 */

class RestAPI() {
    private val myTwitterApiClient: MyTwitterApiClient

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.twitter.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        myTwitterApiClient = retrofit.create(MyTwitterApiClient::class.java)
    }

    fun getShow(id: Long): Call<User> {
        return myTwitterApiClient.show(id)
    }
}