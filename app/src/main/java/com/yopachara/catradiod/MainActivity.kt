package com.yopachara.catradiod

import android.net.Network
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import com.yopachara.catradiod.libraries.radio.RadioListener
import com.yopachara.catradiod.libraries.radio.RadioManager
import com.yopachara.catradiod.services.CatService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity(), RadioListener {
    private val RADIO_URL: String = "http://catradio.mylive.in.th:8000/live"

    internal var mRadioManager: RadioManager = RadioManager.with(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRadioManager.registerListener(this)
        mRadioManager.setLogging(true)
        buttonControlStart.setOnClickListener { view -> initializeUI() }




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
        Log.d(s,s1)
    }

    override fun onError() {

    }
}
