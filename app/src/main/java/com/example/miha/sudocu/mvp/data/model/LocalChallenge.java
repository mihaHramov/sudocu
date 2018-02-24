package com.example.miha.sudocu.mvp.data.model;


public class LocalChallenge {
    private Challenge challenge;
    private Grid localGrid;

    public LocalChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setLocalGame(Grid grid) {
        localGrid = grid;
    }
    public Grid getLocalGame(){
        return localGrid;
    }
}
