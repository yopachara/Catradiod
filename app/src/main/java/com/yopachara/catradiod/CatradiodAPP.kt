package com.yopachara.catradiod

import android.app.Application
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.yopachara.catradiod.injection.component.ApplicationComponent
import com.yopachara.catradiod.injection.component.DaggerApplicationComponent
import com.yopachara.catradiod.injection.module.ApplicationModule
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


/**
 * Created by dw03 on 8/19/2016 AD.
 */

class CatradiodAPP : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private val TWITTER_KEY = "tnDe2dnxLajT3wJPEBBo7XKwu"
    private val TWITTER_SECRET = "Ag1FVQVTjOjvg5HYT1QOAdKsSTrXaxwXaxr6EzDoPPjt10uLXv"

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        val authConfig = TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)
        Fabric.with(this, Twitter(authConfig))
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/superspace_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())
        initDaggerComponent()

    }

    fun initDaggerComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    fun getComponent(): ApplicationComponent {
//        if (applicationComponent == null) {
//            applicationComponent = DaggerApplicationComponent.builder()
//                    .applicationModule(ApplicationModule(this))
//                    .build()
//        }
        return applicationComponent
    }

}