package com.yopachara.catradiod.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.AbsListView
import butterknife.BindView
import butterknife.ButterKnife
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
import com.yopachara.catradiod.data.model.Tag
import com.yopachara.catradiod.data.remote.StickyService
import com.yopachara.catradiod.data.remote.model.Cat
import com.yopachara.catradiod.library.radio.RadioListener
import com.yopachara.catradiod.library.radio.RadioManager
import com.yopachara.catradiod.ui.base.BaseActivity

import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainContract.View, RadioListener, SwipeRefreshLayout.OnRefreshListener, FilterListener<Tag>, AbsListView.OnScrollListener {

    override fun onScroll(p0: AbsListView?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
//        Timber.i("$p0 $p1")

        if (view != null) {
            if (view.id == timeline_list.id) {
                val currentFirstVisibleItem: Int = timeline_list.firstVisiblePosition;

                if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                    mIsScrollingUp = false;
                    slidingLayout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
                } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                    mIsScrollingUp = true;
                    slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                }

                mLastFirstVisibleItem = currentFirstVisibleItem;
            }
        }
    }

    internal var mRadioManager: RadioManager = RadioManager.with(this)
    private var qualitySound = ""

    @BindView(R.id.listToolbar)
    lateinit var mToolbar: Toolbar

    @BindView(R.id.filter)
    lateinit var mFilter: Filter<Tag>

    private var mColors: IntArray? = null
    private var mTitles: Array<String>? = null
    private var mLastFirstVisibleItem: Int = 0
    private var mIsScrollingUp: Boolean = false

    val actionCallback = object : Callback<Tweet>() {
        override fun success(result: Result<Tweet>?) {
            progressBar.visibility = View.GONE
        }

        override fun failure(exception: TwitterException?) {
        }
    }

    @Inject
    lateinit var presenter: MainPresenter

    lateinit var adapter: TweetTimelineListAdapter

    override fun showRibotsEmpty() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSong(cat: Cat) {
        if (cat.now !=null) {
            mRadioManager.updateNotification(cat.now?.song, cat.now?.name, R.drawable.default_art, R.drawable.default_art)
            tv_song_artist.text = cat.now?.song + " " + cat.now?.name
        } else{
            mRadioManager.updateNotification("no","song", R.drawable.default_art, R.drawable.default_art)
            tv_song_artist.text = "no song now"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        Timber.tag("MainActivity")
        setSupportActionBar(mToolbar)
        activityComponent.inject(this)
        presenter.attachView(this)
        val stickyService: Intent = Intent(this, StickyService::class.java)
        startService(stickyService)
        adapter = createTimelineFromQuery("#หนังหน้าแมว")
//        bt_search.setOnClickListener { query(et_filter.text.toString()) }

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
        timeline_list.setOnScrollListener(this)
        mColors = resources.getIntArray(R.array.colors)
        mTitles = resources.getStringArray(R.array.job_titles)

        mFilter.adapter = Adapter(getTags())
        mFilter.listener = this
        mFilter.noSelectedItemText = getString(R.string.str_all_selected)
        mFilter.build()
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
        query(getFilterArray(mTitles!!))
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
            mRadioManager.updateNotification("test", "test", R.drawable.default_art, R.drawable.default_art)
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

    fun createDialogFilterTimeline() {

    }

    override fun onResume() {
        super.onResume()
        mRadioManager.connect()
        if (mRadioManager.isConnected && mRadioManager.isPlaying) {
            textviewControl.text = "RADIO STATE : PLAYING..."
            presenter.loadRibots()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
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
            //            if (disposables.isDisposed) disposables.dispose()
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

    internal inner class Adapter(items: List<Tag>) : FilterAdapter<Tag>(items) {

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
}
