package com.example.miha.sudocu.presenter.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.miha.sudocu.view.activity.MainActivity;
import com.example.miha.sudocu.view.events.BusProvider;
import com.example.miha.sudocu.view.events.OnStopMusicEvent;
import com.squareup.otto.Bus;


public class MyMediaPlayerService extends Service {
    private Bus bus;
    private MediaPlayer player = null;
    @Override
    public void onCreate() {
        bus = BusProvider.getInstance();
        bus.register(this);
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int resultMelody = intent.getIntExtra(MainActivity.myMediaPlayer, 0);
        if(player!=null){
            player.release();//освободил
        }
        if(player==null){
          player = MediaPlayer.create(getApplicationContext(),resultMelody);
        }
        player.setOnCompletionListener(mp -> {
            player.release();
            player = null;
            bus.post(new OnStopMusicEvent());
        });
        player.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }
}
