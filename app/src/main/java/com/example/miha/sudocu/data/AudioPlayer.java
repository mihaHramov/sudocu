package com.example.miha.sudocu.data;

import android.content.Context;
import android.media.MediaPlayer;



public class AudioPlayer {
    private MediaPlayer mPlayer;
    private Context mContext;

    public void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public AudioPlayer(Context context) {
        mContext = context;
    }

    public void play(int resId) {
        stop();
        mPlayer = MediaPlayer.create(mContext, resId);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });
        mPlayer.start();
    }
}
