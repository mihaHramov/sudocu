package com.example.miha.sudocu.presenter.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.miha.sudocu.View.Activity.MainActivity;
import com.example.miha.sudocu.data.model.AudioPlayer;



public class MyMediaPlayerService extends Service {
    private AudioPlayer mPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer = AudioPlayer.getInstance(this);
        int resultMelody = intent.getIntExtra(MainActivity.myMediaPlayer, 0);
        mPlayer.play(resultMelody);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
