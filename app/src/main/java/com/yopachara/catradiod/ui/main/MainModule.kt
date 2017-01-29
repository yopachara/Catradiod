package com.yopachara.catradiod.ui.main

import android.support.v7.app.AppCompatActivity
import com.yopachara.catradiod.data.remote.model.Cat
import com.yopachara.catradiod.ui.base.ActivityModule
import dagger.Module
import dagger.Provides

@Module
class MainModule(activity: AppCompatActivity, val cat: Cat) : ActivityModule(activity) {

    @Provides
    fun provideRepo(): Cat = cat
}