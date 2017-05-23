package com.example.miha.sudocu.data.DP;


import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.model.User;

import java.util.ArrayList;

public interface ChallengeDP {
    interface ChallengeDPGetAllScoreCallbacks {
        void onSuccess(ArrayList<Challenge> challenges);

        void onError();
    }
    interface ChallengeDPSendGameCallbacks{
        void onSuccess(Object response);

        void onError();
    }

    void sendGame(User user, Grid grid, ChallengeDPSendGameCallbacks callbacks);

    void getAllScore(ChallengeDPGetAllScoreCallbacks callbacks);
}
