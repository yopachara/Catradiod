package com.yopachara.catradiod.ui.main

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.Toast
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.SearchTimeline
import com.twitter.sdk.android.tweetui.TimelineResult
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.yopachara.catradiod.ApplicationComponent
import com.yopachara.catradiod.R
import com.yopachara.catradiod.adapter.CustomTweetTimelineListAdapter
import com.yopachara.catradiod.data.remote.StickyService
import com.yopachara.catradiod.library.radio.RadioListener
import com.yopachara.catradiod.library.radio.RadioManager
import com.yopachara.catradiod.databinding.ActivityMainBinding

import com.yopachara.catradiod.ui.base.ViewModelActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.reactivestreams.Subscription
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : ViewModelActivity<MainViewModel, ActivityMainBinding>(), RadioListener, SwipeRefreshLayout.OnRefreshListener {
    internal var mRadioManager: RadioManager = RadioManager.with(this)
    private var qualitySound = ""
    val disposables: CompositeDisposable = CompositeDisposable()

    val actionCallback = object : Callback<Tweet>() {
        override fun success(result: Result<Tweet>?) {
            progressBar.visibility = View.GONE
        }

        override fun failure(exception: TwitterException?) {
        }
    }

    private val searchTimeline: SearchTimeline = SearchTimeline.Builder().query("#หนังหน้าแมว").build()
    val adapter: CustomTweetTimelineListAdapter = CustomTweetTimelineListAdapter(this, searchTimeline)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        Timber.tag("MainActivity")
        Timber.d("MainActivity Created")
        setSupportActionBar(binding.listToolbar)

        val stickyService: Intent = Intent(this, StickyService::class.java)
        startService(stickyService)
    }


    override fun onBind() {
        super.onBind()

        progressBar.isIndeterminate = false
        progressBar.progress = 0

        qualitySound = resources.getString(R.string.radio_url_hq)
        mRadioManager.registerListener(this)
        mRadioManager.setLogging(true)
        notification_play.setOnClickListener { view -> initializeUI() }
        notification_collapse.setOnClickListener {
            mRadioManager.stopRadio()
        }
        slidingLayout.anchorPoint = 0.5f
        slidingLayout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {
                Log.i("onPanelSlide", "onPanelSlide, offset " + slideOffset)
            }

            override fun onPanelStateChanged(panel: View, previousState: SlidingUpPanelLayout.PanelState, newState: SlidingUpPanelLayout.PanelState) {
                Log.i("onPanelStateChanged", "onPanelStateChanged " + newState)
            }
        })
        swipeRefreshLayout.setOnRefreshListener(this)
        timeline_list.adapter = adapter


        disposables.add(viewModel.loadingState().subscribe {
            binding.swipeRefreshLayout.isRefreshing = it
        })

        disposables.add(viewModel.fetchErrors().subscribe {
            //            errorFetchRepos()
        })

        disposables.add(viewModel.networkErrors().subscribe {
            //            errorNoNetwork()
        })
//

    }

    override fun getViewBinding(): ActivityMainBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun injectDependencies(graph: ApplicationComponent) {
        graph.plus(MainModule(this))
                .injectTo(this)
    }


    override fun onRefresh() {
        adapter.refresh(object : Callback<TimelineResult<Tweet>>() {
            override fun success(result: Result<TimelineResult<Tweet>>?) {
                Log.d("onRefresh", "success")
                binding.swipeRefreshLayout.isRefreshing = false
            }

            override fun failure(exception: TwitterException?) {
                Log.d("onRefresh", exception.toString())
            }
        })
    }

    fun initializeUI() {
        if (!mRadioManager.isPlaying) {
            Log.d("Type of quality", qualitySound)
            mRadioManager.startRadio(qualitySound)
            viewModel.fetchSong()
            disposables.add(viewModel.getSong().subscribe {
                cat ->
                binding.tvSongArtist.text = cat.next?.song
                mRadioManager.updateNotification(cat.next?.song, cat.next?.name, R.drawable.default_art, R.drawable.default_art)
            })

//            mRadioManager.updateNotification("test", "test", R.drawable.default_art, R.drawable.default_art)
        } else {
            mRadioManager.stopRadio()
        }
    }

    override fun onResume() {
        super.onResume()
        mRadioManager.connect()
    }


    override fun onDestroy() {
        super.onDestroy()
        mRadioManager.disconnect()
    }

    override fun onRadioLoading() {
        runOnUiThread {
            //TODO Do UI works here.
            textviewControl.text = "RADIO STATE : LOADING..."
        }
    }

    override fun onRadioClosed() {
        runOnUiThread {
            if (disposables.isDisposed) disposables.dispose()
        }
    }


    override fun onRadioConnected() {

    }

    override fun onRadioStarted() {
        runOnUiThread {
            //TODO Do UI works here.
            textviewControl.text = "RADIO STATE : PLAYING..."

        }
    }

    override fun onRadioStopped() {
        runOnUiThread {
            //TODO Do UI works here
            textviewControl.text = "RADIO STATE : STOPPED."
        }
    }

    override fun onMetaDataReceived(s: String, s1: String) {
        //TODO Check metadata values. Singer name, song name or whatever you have.
        Log.d(s, s1)
    }

    override fun onError() {

    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_toggle -> {
                if (slidingLayout != null) {
                    if (slidingLayout.panelState != SlidingUpPanelLayout.PanelState.HIDDEN) {
                        slidingLayout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                        item.setTitle(R.string.action_show)
                    } else {
                        slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                        item.setTitle(R.string.action_hide)
                    }
                }
                return true
            }
            R.id.action_quality -> {
                if (qualitySound != resources.getString(R.string.radio_url_hq)) {
                    qualitySound = resources.getString(R.string.radio_url_hq)
                    item.setTitle(R.string.action_quality_lq)
                    Log.d("Type of quality", qualitySound)
                } else {
                    qualitySound = resources.getString(R.string.radio_url_lq)
                    item.setTitle(R.string.action_quality_hq)
                    Log.d("Type of quality", qualitySound)
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
