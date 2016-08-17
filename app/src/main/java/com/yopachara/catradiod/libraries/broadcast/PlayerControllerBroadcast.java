package com.yopachara.catradiod.libraries.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yopachara.catradiod.libraries.media.MediaManager;
import com.yopachara.catradiod.libraries.media.MediaPlayerService;
import com.yopachara.catradiod.libraries.radio.RadioManager;
import com.yopachara.catradiod.libraries.radio.RadioPlayerService;


/**
 * Created by mertsimsek on 04/11/15.
 */
public class PlayerControllerBroadcast extends BroadcastReceiver {

    /**
     * This receiver receives STOP controlling between player services
     * For instances, If MediaPlayerService is running and playing stream
     * and then RadioPlayerService requested play radio stream, Service sends broadcast
     * that stop MediaPlayerService. And If RadioPlayerService is running and
     * playing radio stream, RadioPlayerService will be stopped here when MediaPlayerService
     * requested play media. This is the way we communicate between services to stop
     * each other.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isRadioServiceBinded = RadioManager.getService() == null ? false : true;
        boolean isMediaServiceBinded = MediaManager.getService() == null ? false : true;

        String action = intent.getAction();

        if(action.equals(MediaPlayerService.ACTION_RADIOPLAYER_STOP)
                && isRadioServiceBinded
                && RadioManager.getService().isPlaying()){
            RadioManager.getService().stop();
        }
        else if(action.equals(RadioPlayerService.ACTION_MEDIAPLAYER_STOP)
                && isMediaServiceBinded
                && MediaManager.getService().isPlaying()){
            MediaManager.getService().stop();
        }
    }
}
