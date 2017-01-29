package com.yopachara.catradiod.ui.main

import com.yopachara.catradiod.ui.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(
        DetailModule::class
))
interface MainComponent {
    fun injectTo(activity: DetailActivity)
}