package com.yopachara.catradiod.injection.component

import android.app.Application
import android.content.Context
import com.yopachara.catradiod.data.DataManager
import com.yopachara.catradiod.data.SyncService
import dagger.Component
import com.yopachara.catradiod.injection.ApplicationContext
import com.yopachara.catradiod.injection.module.ApplicationModule
import com.yopachara.catradiod.injection.module.DataModule

import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, DataModule::class))
interface ApplicationComponent {
    fun inject(syncService: SyncService)

    @ApplicationContext fun context(): Context
    fun application(): Application
//    fun ribotsService(): RibotsService
//    fun databaseHelper(): DatabaseHelper
    fun dataManager(): DataManager
}
