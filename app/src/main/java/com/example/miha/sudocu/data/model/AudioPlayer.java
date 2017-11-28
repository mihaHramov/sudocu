package com.example.miha.sudocu.data.model;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.miha.sudocu.data.DP.IAudioCompleteCallback;

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

    private void stop() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private AudioPlayer(Context context) {
        mContext = context;
    }

    public void play(int resId, IAudioCompleteCallback callback) {
        stop();
        mPlayer = MediaPlayer.create(mContext, resId);
        mPlayer.setOnCompletionListener(mp -> {
            stop();
            if(callback!=null) callback.execute();
        });
        mPlayer.start();
    }
}
