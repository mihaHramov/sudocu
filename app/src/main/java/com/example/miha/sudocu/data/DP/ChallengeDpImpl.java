package com.example.miha.sudocu.data.DP;

import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

import rx.Observable;


public class ChallengeDpImpl implements ChallengeDP {

    private ChallengeApi instance;

    public ChallengeDpImpl(ChallengeApi instance) {
        this.instance = instance;
    }

    @Override
    public Observable<Void> sendGame(User user, Grid grid) {
        Gson g = new Gson();
        return instance.addChallenge(g.toJson(grid));
    }

    @Override
    public Observable<ArrayList<Challenge>> getAllScore() {
        return instance.getAllScore();
    }
}
