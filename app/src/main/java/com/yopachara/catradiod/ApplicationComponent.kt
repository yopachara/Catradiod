package com.yopachara.catradiod

import dagger.Component
import com.yopachara.catradiod.data.network.NetworkModule
import com.yopachara.catradiod.data.remote.ApiModule
import com.yopachara.catradiod.ui.main.MainComponent
import com.yopachara.catradiod.ui.main.MainModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        NetworkModule::class,
        ApiModule::class
))
interface ApplicationComponent {

    // Injectors
    fun injectTo(app: CatradiodAPP)

    // Submodule methods
    // Every screen is its own submodule of the graph and must be added here.
    fun plus(module: MainModule): MainComponent
//    fun plus(module: MainModule): MainComponent
}