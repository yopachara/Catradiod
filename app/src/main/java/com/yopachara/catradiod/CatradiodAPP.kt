package com.yopachara.catradiod

import android.app.Application
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import io.fabric.sdk.android.Fabric

/**
 * Created by dw03 on 8/19/2016 AD.
 */

class CatradiodAPP: Application() {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private val TWITTER_KEY = "tnDe2dnxLajT3wJPEBBo7XKwu"
    private val TWITTER_SECRET = "Ag1FVQVTjOjvg5HYT1QOAdKsSTrXaxwXaxr6EzDoPPjt10uLXv"
    override fun onCreate() {
        super.onCreate()
        instance = this
        val authConfig = TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET)
        Fabric.with(this, Twitter(authConfig))
    }

    companion object {
        lateinit var instance: CatradiodAPP
            private set
    }
}