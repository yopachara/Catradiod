package com.yopachara.catradiod.injection.component

import dagger.Subcomponent
import com.yopachara.catradiod.injection.PerActivity
import com.yopachara.catradiod.injection.module.ActivityModule
import com.yopachara.catradiod.ui.main.MainActivity

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}
