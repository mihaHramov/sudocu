package com.example.miha.sudocu.mvp.view.events;


public class PlayMusicEvent {
    private int resMusic;

    public int getResMusic() {
        return resMusic;
    }

    public PlayMusicEvent(int resMusic) {

        this.resMusic = resMusic;
    }
}
