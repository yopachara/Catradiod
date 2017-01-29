package com.yopachara.catradiod

import android.app.Application
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import javax.inject.Inject
import dagger.Lazy


/**
 * Created by dw03 on 8/19/2016 AD.
 */

class CatradiodAPP: Application() {

    @Inject
    lateinit var debugTree: Lazy<Timber.DebugTree>

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private val TWITTER_KEY = "tnDe2dnxLajT3wJPEBBo7XKwu"
    private val TWITTER_SECRET = "Ag1FVQVTjOjvg5HYT1QOAdKsSTrXaxwXaxr6EzDoPPjt10uLXv"

    companion object {
        lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDependencyGraph()

        if (BuildConfig.DEBUG) {
            Timber.plant(debugTree.get())
        }
        val authConfig = TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)
        Fabric.with(this, Twitter(authConfig))
    }

    private fun initDependencyGraph() {
        graph = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        graph.injectTo(this)
    }
}