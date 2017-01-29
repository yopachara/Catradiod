package com.yopachara.catradiod.ui.main

import android.support.v7.app.AppCompatActivity
import com.yopachara.catradiod.ui.base.ActivityModule
import dagger.Module
import dagger.Provides
import io.github.plastix.kotlinboilerplate.data.remote.model.Repo
import io.github.plastix.kotlinboilerplate.ui.base.ActivityModule

@Module
class MainModule(activity: AppCompatActivity, val repo: Repo) : ActivityModule(activity) {

    @Provides
    fun provideRepo(): Repo = repo
}