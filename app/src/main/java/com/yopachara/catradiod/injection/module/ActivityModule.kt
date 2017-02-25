package com.yopachara.catradiod.injection.module

import android.app.Activity
import android.content.Context
import com.yopachara.catradiod.injection.ActivityContext
import com.yopachara.catradiod.injection.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @PerActivity
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @PerActivity
    @ActivityContext
    internal fun providesContext(): Context {
        return activity
    }
}
