package com.example.miha.sudocu.mvp.view.events;

public class OnChangeHistoryGame {
    private Boolean forward;

    public OnChangeHistoryGame(Boolean forward) {
        this.forward = forward;
    }

    public Boolean getForward() {
        return forward;
    }
}
