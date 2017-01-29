package com.yopachara.catradiod.ui.main

import com.yopachara.catradiod.ui.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(
        MainModule::class
))
interface MainComponent {
    fun injectTo(activity: MainActivity)
}