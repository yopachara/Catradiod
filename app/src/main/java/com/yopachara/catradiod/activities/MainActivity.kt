package com.yopachara.catradiod.activities

import android.net.Network
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.yopachara.catradiod.R
import kotlinx.android.synthetic.main.activity_main.*
import com.yopachara.catradiod.libraries.radio.RadioListener
import com.yopachara.catradiod.libraries.radio.RadioManager

class MainActivity : AppCompatActivity(), RadioListener {
    private val RADIO_URL: String = "http://catradio.mylive.in.th:8000/live"
    internal var mRadioManager: RadioManager = RadioManager.with(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRadioManager.registerListener(this)
        mRadioManager.setLogging(true)
        buttonControlStart.setOnClickListener { view -> initializeUI() }

        slidingLayout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {
                Log.i("onPanelSlide", "onPanelSlide, offset " + slideOffset)
            }

            override fun onPanelStateChanged(panel: View, previousState: SlidingUpPanelLayout.PanelState, newState: SlidingUpPanelLayout.PanelState) {
                Log.i("onPanelStateChanged", "onPanelStateChanged " + newState)
            }
        })


    }

    fun initializeUI() {
        if (!mRadioManager.isPlaying)
            mRadioManager.startRadio(RADIO_URL)
        else
            mRadioManager.stopRadio()
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
}
