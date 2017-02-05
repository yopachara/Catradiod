package com.yopachara.catradiod.data.remote;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yopachara.catradiod.library.radio.RadioManager;


public class StickyService extends Service {
    private SharedPreferences mPrefs;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        if(RadioManager.getService() != null)
            RadioManager.getService().stopFromNotification();
    }
}