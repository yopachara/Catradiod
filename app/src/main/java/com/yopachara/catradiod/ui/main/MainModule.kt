package com.yopachara.catradiod.ui.main

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.yopachara.catradiod.ui.base.ActivityModule
import dagger.Module
import dagger.Provides

@Module
class MainModule(activity: AppCompatActivity) : ActivityModule(activity) {

    @Provides
    fun provideLinearLayoutManager(context: Context): LinearLayoutManager = LinearLayoutManager(context)
}