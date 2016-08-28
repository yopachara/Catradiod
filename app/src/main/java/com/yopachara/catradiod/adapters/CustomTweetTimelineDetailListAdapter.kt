package com.yopachara.catradiod.adapters

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.Timeline
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.yopachara.catradiod.activities.EmbeddedTweetsActivity

/**
 * Created by yopachara on 8/28/2016 AD.
 */

class CustomTweetTimelineDetailListAdapter(context: Context, timeline: Timeline<Tweet>) : TweetTimelineListAdapter(context, timeline) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getView(position, convertView, parent)

        //disable subviews
        if (view is ViewGroup) {
            disableViewAndSubViews(view)
        }

        //enable root view and attach custom listener
        view.isEnabled = true
        view.setOnClickListener {
            val tweetId = "click tweetId:" + getItemId(position)
            Toast.makeText(context, tweetId, Toast.LENGTH_SHORT).show()
            val i = Intent(context, EmbeddedTweetsActivity::class.java)
            i.putExtra("tweetId",getItemId(position))
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.applicationContext.startActivity(i)
        }
        return view
    }

    private fun disableViewAndSubViews(layout: ViewGroup) {
        layout.isEnabled = false
        for (i in 0..layout.childCount - 1) {
            val child = layout.getChildAt(i)
            if (child is ViewGroup) {
                disableViewAndSubViews(child)
            } else {
                child.isEnabled = false
                child.isClickable = false
                child.isLongClickable = false
            }
        }
    }

}
