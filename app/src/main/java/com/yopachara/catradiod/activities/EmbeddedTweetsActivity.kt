package com.yopachara.catradiod.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.TweetUtils
import com.twitter.sdk.android.tweetui.TweetView
import com.yopachara.catradiod.R
import kotlinx.android.synthetic.main.activity_embedded_tweets.*
import java.util.*

class EmbeddedTweetsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tweetIds = intent.getLongExtra("tweetId",510908133917487104)
        setContentView(R.layout.activity_embedded_tweets)
        Toast.makeText(this, tweetIds.toString(), Toast.LENGTH_SHORT).show()

        TweetUtils.loadTweet(tweetIds, object: Callback<Tweet>(){
            override fun success(result: Result<Tweet>) {
                my_tweet_layout.addView(TweetView(this@EmbeddedTweetsActivity, result.data))
                val twitterApiClient = TwitterCore.getInstance().getApiClient()
                val statusesService = twitterApiClient.statusesService
                val call = statusesService.show(524971209851543553L, null, null, null)
                call.enqueue(object: Callback<Tweet>(){
                    override fun failure(exception: TwitterException?) {
                        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun success(result: Result<Tweet>?) {
                        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
            }

            override fun failure(exception: TwitterException?) {
                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }
}
