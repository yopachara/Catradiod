package com.yopachara.catradiod.ui.main

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context


/**
 * Created by yopachara on 2/26/2017 AD.
 */

class MyBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val intent1 = Intent(context, MainActivity::class.java)
        intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent1)
    }
}