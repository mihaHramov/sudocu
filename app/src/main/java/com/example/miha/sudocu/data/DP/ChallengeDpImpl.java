package com.example.miha.sudocu.data.DP;

import com.example.miha.sudocu.api.ChallengeApi;
import com.example.miha.sudocu.data.DP.intf.ChallengeDP;
import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.model.User;

import java.util.ArrayList;

import rx.Observable;


public class ChallengeDpImpl implements ChallengeDP {

    private ChallengeApi instance;

    public ChallengeDpImpl(ChallengeApi instance) {
        this.instance = instance;
    }

    @Override
    public Observable<Object> sendGame(User user, Grid grid) {
        Challenge challenge = new Challenge(user.getName(),user.getPassword(),grid);
        return instance.addChallenge(challenge);
    }

    @Override
    public Observable<ArrayList<Challenge>> getAllScore() {
        return instance.getAllScore();
    }
}
