package com.yopachara.catradiod.injection.module

import android.provider.SyncStateContract
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yopachara.catradiod.data.remote.ApiConstants
import com.yopachara.catradiod.data.remote.CatService
import com.yopachara.catradiod.data.remote.CatWebService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @Singleton
    fun provideRibotsService(okHttpClient: OkHttpClient, gson: Gson): CatService {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiConstants.CAT_BASE_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(CatService::class.java)
    }

    @Provides
    @Singleton
    fun providesCatWebService(okHttpClient: OkHttpClient, gson: Gson): CatWebService {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiConstants.CAT_WEBSERVICE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(CatWebService::class.java)
    }
}
