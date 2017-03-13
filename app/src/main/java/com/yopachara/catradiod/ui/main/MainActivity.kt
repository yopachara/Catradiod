package com.yopachara.catradiod.ui.main

import android.app.DialogFragment
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.AbsListView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.BitmapRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.github.nisrulz.sensey.Sensey
import com.github.nisrulz.sensey.TouchTypeDetector
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.SearchTimeline
import com.twitter.sdk.android.tweetui.TimelineResult
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.yalantis.filter.adapter.FilterAdapter
import com.yalantis.filter.listener.FilterListener
import com.yalantis.filter.widget.Filter
import com.yalantis.filter.widget.FilterItem
import com.yopachara.catradiod.R
import com.yopachara.catradiod.data.SyncService
import com.yopachara.catradiod.data.model.DjSchedule
import com.yopachara.catradiod.data.model.Program
import com.yopachara.catradiod.data.model.Tag
import com.yopachara.catradiod.data.remote.StickyService
import com.yopachara.catradiod.data.remote.model.Cat
import com.yopachara.catradiod.extension.TextUtil
import com.yopachara.catradiod.library.radio.RadioListener
import com.yopachara.catradiod.library.radio.RadioManager
import com.yopachara.catradiod.ui.about.AboutActivity
import com.yopachara.catradiod.ui.about.MyDialogFragment
import com.yopachara.catradiod.ui.base.BaseActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.component_schedule.*
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus

class MainActivity : BaseActivity(), MainContract.View, RadioListener, SwipeRefreshLayout.OnRefreshListener, FilterListener<Tag>, TouchTypeDetector.TouchTypListener {


    internal var mRadioManager: RadioManager = RadioManager.with(this)
    private var qualitySound = ""

    @BindView(R.id.listToolbar)
    lateinit var mToolbar: Toolbar

    @BindView(R.id.filter)
    lateinit var mFilter: Filter<Tag>

    @BindView(R.id.slidingLayout)
    lateinit var mSliding: SlidingUpPanelLayout
    private var mColors: IntArray? = null
    private var mTitles: Array<String>? = null
    private var mIsScrollingUp: Boolean = false
    @Inject
    lateinit var presenter: MainPresenter

    lateinit var adapter: TweetTimelineListAdapter
    lateinit private var filterAdapter: Adapter
    override fun showRibotsEmpty() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDj(dj: Program) {
        tv_title.text = "รายการ ${dj.shiftTitle}"
        tv_desc.text = dj.shiftDesc
        tv_shift.text = "${dj.shiftStart} - ${dj.shiftEnd}"
        for (d in dj.dj) {
            if (!tv_dj.text.equals("")) {
                tv_dj.text = tv_dj.text.toString() + ", " + d.fullName
            } else {
                tv_dj.text = d.fullName
            }

        }
        setProgramImg(dj.shiftThumb)
    }

    fun setProgramImg(url: String) {
        Glide.with(this)
                .load(url)
                .fitCenter()
                .placeholder(R.drawable.cat_placeholder)
                .into(iv_logo)
    }


    override fun showSong(cat: Cat) {
        if (cat.now != null) {
            getImage(cat)
            tv_song_artist.text = "${cat.now?.song} - ${cat.now?.name}"
        } else {
            presenter.setDjToNotification()
//            mRadioManager.updateNotification("no", "song", R.drawable.ic_launcher_app, R.drawable.ic_launcher_app)
            notification_image.setImageResource(R.drawable.ic_album_black_48dp)
            tv_song_artist.text = getString(R.string.no_song_available)
        }

    }

    override fun showDjNoti(dj: Program) {

        try {
            val url: String = dj.shiftThumb
            Glide.with(applicationContext)
                    .load(url)
                    .asBitmap()
                    .into(object : SimpleTarget<Bitmap>(250, 250) {
                        override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>?) {
                            mRadioManager.updateNotification(dj.shiftTitle, dj.shortDesc, R.drawable.ic_launcher_app, resource)
                            notification_image.setImageBitmap(resource)
                        }
                    })
        } catch (e: Exception) {
            Timber.e(e)
            mRadioManager.updateNotification(dj.shiftTitle, dj.shortDesc, R.drawable.ic_launcher_app, R.drawable.ic_launcher_app)
            notification_image.setImageResource(R.drawable.ic_album_black_48dp)
        }

    }

    fun getImage(cat: Cat) {
        try {
            val url: String = "http://cms.thisiscat.tk/admin/pix/single/${cat.now!!.id}_medium.jpg"
            Glide.with(applicationContext)
                    .load(url)
                    .asBitmap()
                    .into(object : SimpleTarget<Bitmap>(250, 250) {
                        override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>?) {
                            mRadioManager.updateNotification(cat.now?.song, cat.now?.name, R.drawable.ic_launcher_app, resource)
                            notification_image.setImageBitmap(resource)
                        }
                    })
        } catch (e: Exception) {
            Timber.e(e)
            mRadioManager.updateNotification(cat.now?.song, cat.now?.name, R.drawable.ic_launcher_app, R.drawable.ic_launcher_app)
            notification_image.setImageResource(R.drawable.ic_album_black_48dp)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        Timber.tag("MainActivity")
        setSupportActionBar(mToolbar)
        presenter.attachView(this)
        startService(Intent(this, StickyService::class.java))
        startService(SyncService.getStartIntent(applicationContext))

        Sensey.getInstance().init(this)

        adapter = createTimelineFromQuery("#หนังหน้าแมว")
//        bt_search.setOnClickListener { query(et_filter.text.toString()) }

        progressBar.isIndeterminate = false
        progressBar.progress = 0

        qualitySound = resources.getString(R.string.radio_url_hq)
        mRadioManager.registerListener(this)
        mRadioManager.setLogging(true)
        notification_play.setOnClickListener { initializeUI() }
        notification_collapse.setOnClickListener {
            mRadioManager.stopRadio()
            presenter.clearComposite()
        }
        mSliding.anchorPoint = 0.5f
        mSliding.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {
                Log.i("onPanelSlide", "onPanelSlide, offset " + slideOffset)
            }

            override fun onPanelStateChanged(panel: View, previousState: SlidingUpPanelLayout.PanelState, newState: SlidingUpPanelLayout.PanelState) {
                Log.i("onPanelStateChanged", "onPanelStateChanged " + newState)
            }
        })
        mSliding.setFadeOnClickListener {
            mSliding.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED;
        }
        swipeRefreshLayout.setOnRefreshListener(this)
        timeline_list.adapter = adapter
        mColors = resources.getIntArray(R.array.colors)
        mTitles = resources.getStringArray(R.array.job_titles)


        filterAdapter = Adapter(getTags())
        mFilter.adapter = filterAdapter

        mFilter.listener = this
        mFilter.noSelectedItemText = getString(R.string.str_all_selected)
        mFilter.build()
        presenter.syncDj()

    }


    override fun onFilterDeselected(item: Tag) {
        Timber.i(item.getText())
    }

    override fun onFilterSelected(item: Tag) {
        Timber.i(item.getText())
//        if (item.getText().equals(mTitles!![0])) {
//            mFilter.deselectAll()
//            mFilter.collapse()
//        }
    }

    override fun onFiltersSelected(filters: ArrayList<Tag>) {
        query(getFilterList(filters))
        Timber.i(filters.toString())
    }


    override fun onNothingSelected() {
        query("#หนังหน้าแมว")
        Timber.i("onNothingSelected")
    }

    fun getFilterArray(filters: Array<String>): String {
        var text: String = ""
        for (filter in filters) {
            if (text == "") {
                text = filter
            } else {
                text.plus(" " + filter)
            }
        }
        return text
    }

    fun getFilterList(filters: ArrayList<Tag>): String {
        var text: String = ""
        for (filter in filters) {
            if (text == "") {
                text = filter.getText()
            } else {
                text.plus(" " + filter.getText())
            }
        }
        return text
    }

    private fun getTags(): List<Tag> {
        val tags = mTitles!!.indices.mapTo(ArrayList<Tag>()) { Tag(mTitles!![it], mColors!![it]) }

        return tags
    }

    override fun onRefresh() {
        adapter.refresh(object : Callback<TimelineResult<Tweet>>() {
            override fun success(result: Result<TimelineResult<Tweet>>?) {
                Log.d("onRefresh", "success")
                swipeRefreshLayout.isRefreshing = false
                adapter.notifyDataSetChanged()
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
//            viewModel.fetchSong()
//            disposables.add(viewModel.getSong().subscribe {
//                cat ->
//                binding.tvSongArtist.text = cat.next?.song
//                mRadioManager.updateNotification(cat.next?.song, cat.next?.name, R.drawable.default_art, R.drawable.default_art)
//            })
            presenter.loadRibots()
        } else {
            mRadioManager.stopRadio()
        }
    }

    fun query(name: String?) {
        timeline_list.adapter = createTimelineFromQuery("$name")
        onRefresh()
    }

    fun createQuery(name: String?): SearchTimeline {
        return SearchTimeline.Builder()
                .query("$name")
                .build()
    }

    fun createTimelineFromQuery(name: String?): TweetTimelineListAdapter {
        return TweetTimelineListAdapter.Builder(this)
                .setTimeline(createQuery("$name"))
                .build()
    }

    override fun onResume() {
        super.onResume()
        mRadioManager.connect()
        Sensey.getInstance().startTouchTypeDetection(applicationContext, this)
        if (mRadioManager.isConnected && mRadioManager.isPlaying) {
            textviewControl.text = getString(R.string.radio_state_playing)
            presenter.loadRibots()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Sensey.getInstance().stop()
        presenter.detachView()
        mRadioManager.disconnect()
    }

    override fun onPause() {
        super.onPause()
        Sensey.getInstance().stopTouchTypeDetection()
    }

    override fun onRadioLoading() {
        runOnUiThread {
            //TODO Do UI works here.
            textviewControl.text = getString(R.string.radio_state_loading)
        }
    }

    override fun onRadioClosed() {
        runOnUiThread {
            //            if (disposables.isDisposed) disposables.dispose()
        }
    }


    override fun onRadioConnected() {

    }

    override fun onRadioStarted() {
        runOnUiThread {
            //TODO Do UI works here.
            textviewControl.text = getString(R.string.radio_state_playing)

        }
    }

    override fun onRadioStopped() {
        runOnUiThread {
            //TODO Do UI works here
            textviewControl.text = getString(R.string.radio_state_stopped)
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
                if (mSliding.panelState != SlidingUpPanelLayout.PanelState.HIDDEN) {
                    mSliding.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                    item.setTitle(R.string.action_show)
                } else {
                    mSliding.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    item.setTitle(R.string.action_hide)
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
            R.id.action_about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }


    internal inner class Adapter(items: List<Tag>) : FilterAdapter<Tag>(items) {
        override var items: List<Tag> = items
            get() = super.items

        fun setNewData(items: List<Tag>) {
            this.items = items
        }

        override fun createView(position: Int, item: Tag): FilterItem {
            val filterItem = FilterItem(this@MainActivity)

            filterItem.strokeColor = mColors!![0]
            filterItem.textColor = mColors!![0]
            filterItem.checkedTextColor = ContextCompat.getColor(this@MainActivity, android.R.color.white)
            filterItem.color = ContextCompat.getColor(this@MainActivity, android.R.color.white)
            filterItem.checkedColor = (mColors as IntArray)[position]
            filterItem.text = item.getText()
            filterItem.deselect()

            return filterItem
        }
    }

    fun showDialog() {
        // Create the fragment and show it as a dialog.
        val newFragment: DialogFragment = MyDialogFragment.newInstance();
        newFragment.show(fragmentManager, "dialog");
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }


    override fun onScroll(scrollDirection: Int) {
        when (scrollDirection) {
            TouchTypeDetector.SCROLL_DIR_UP -> {
                // Scrolling Up
                if (!mIsScrollingUp) {
                    Timber.d("Scrolling Up")
                    mSliding.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN)
                    appbar.setExpanded(false, true)
                    mIsScrollingUp = true
                }
            }

            TouchTypeDetector.SCROLL_DIR_DOWN -> {
                // Scrolling Down
                if (mIsScrollingUp) {
                    Timber.d("Scrolling Down")
                    mSliding.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                    appbar.setExpanded(true, true)
                    mIsScrollingUp = false
                }

            }

            TouchTypeDetector.SCROLL_DIR_LEFT -> {
                // Scrolling Left
//                mSliding.panelState = SlidingUpPanelLayout.PanelState.HIDDEN


            }
            TouchTypeDetector.SCROLL_DIR_RIGHT -> {
//                mSliding.panelState = SlidingUpPanelLayout.PanelState.HIDDEN

                // Scrolling Right

            }
            else -> {
                // Do nothing
            }

        }
    }

    override fun onDoubleTap() {
    }

    override fun onSwipe(swipeDirection: Int) {
    }

    override fun onSingleTap() {
    }

    override fun onLongPress() {
    }

    override fun onTwoFingerSingleTap() {
    }

    override fun onThreeFingerSingleTap() {
    }
}
