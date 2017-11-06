package com.example.miha.sudocu.data.DP;


import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.model.User;

import java.util.ArrayList;

import rx.Observable;

public interface ChallengeDP {
    Observable<Object> sendGame(User user, Grid grid);
    Observable<ArrayList<Challenge>> getAllScore();
}
