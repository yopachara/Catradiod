package com.yopachara.catradiod.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.SearchTimeline
import com.twitter.sdk.android.tweetui.TimelineResult
import com.twitter.sdk.android.tweetui.TweetUtils
import com.twitter.sdk.android.tweetui.TweetView
import com.yopachara.catradiod.R
import com.yopachara.catradiod.adapters.CustomTweetTimelineDetailListAdapter
import com.yopachara.catradiod.adapters.CustomTweetTimelineListAdapter
import kotlinx.android.synthetic.main.activity_tweets_details.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class TweetsTimelineDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tweetIds = intent.getLongExtra("tweetId", 510908133917487104)
        setContentView(R.layout.activity_tweets_details)
        Toast.makeText(this, tweetIds.toString(), Toast.LENGTH_SHORT).show()
        Log.i("tweetIds", tweetIds.toString())
        val searchTimeline: SearchTimeline = SearchTimeline.Builder().query(tweetIds.toString()).build()
        val adapter: CustomTweetTimelineDetailListAdapter = CustomTweetTimelineDetailListAdapter(this, searchTimeline)
        val lv = findViewById(R.id.timeline_list_detail) as ListView

//        swipeRefreshLayoutDetail.setOnRefreshListener(this)
        lv.setAdapter(adapter)
        swipeRefreshLayoutDetail.setOnRefreshListener {
            adapter.refresh(object : Callback<TimelineResult<Tweet>>() {
                override fun success(result: Result<TimelineResult<Tweet>>?) {
                    Log.d("onRefresh", "success")
                    swipeRefreshLayoutDetail.setRefreshing(false);
                }

                override fun failure(exception: TwitterException?) {
                    Log.d("onRefresh", exception.toString())
                }
            })

        }

//

    }

}
