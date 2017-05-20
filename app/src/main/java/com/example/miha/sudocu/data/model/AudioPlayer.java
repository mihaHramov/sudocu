package com.example.miha.sudocu.data.model;

import android.content.Context;
import android.media.MediaPlayer;


public class AudioPlayer {
    private MediaPlayer mPlayer = null;
    private Context mContext;
    private static AudioPlayer player = null;

    public static AudioPlayer getInstance(Context context) {
        if (player == null) {
            player = new AudioPlayer(context);
        }
        return player;
    }

    public void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private AudioPlayer(Context context) {
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
