package com.example.miha.sudocu.mvp.data.DP.intf;


import com.example.miha.sudocu.mvp.data.model.Challenge;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.data.model.User;

import java.util.ArrayList;

import rx.Observable;

public interface ChallengeDP {
    Observable<Object> sendGame(User user, Grid grid);
    Observable<ArrayList<Challenge>> getAllScore();
}
