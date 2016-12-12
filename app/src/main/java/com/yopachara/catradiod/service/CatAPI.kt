package com.yopachara.catradiod.service

import com.twitter.sdk.android.core.models.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.Observable

/**
 * Created by yopachara on 9/11/2016 AD.
 */
class CatAPI() {
    private val myCatApiClient: CatService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.thisiscat.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        myCatApiClient = retrofit.create(CatService::class.java)
    }

    fun getShow(): Call<String> {
        return myCatApiClient.getSong()
    }
}