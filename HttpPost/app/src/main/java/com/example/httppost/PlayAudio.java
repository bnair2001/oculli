package com.example.httppost;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class PlayAudio extends Service {

    MediaPlayer mp;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    public void onCreate()
    {
        //mp = MediaPlayer.create(this, R.raw.b);
        mp.setLooping(false);
    }
    public void onDestroy()
    {
        mp.stop();
    }
    public void onStart(Intent intent,int startid){

        //Log.d(tag, "On start");
        mp.start();
    }
}