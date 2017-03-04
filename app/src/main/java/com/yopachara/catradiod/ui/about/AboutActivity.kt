package com.yopachara.catradiod.ui.about

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.yopachara.catradiod.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_about)
        AboutHelper.with(this).init().loadAbout()
    }
}
