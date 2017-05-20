package com.example.miha.sudocu.data.DP;


import com.example.miha.sudocu.data.model.Challenge;

import java.util.ArrayList;

public interface ChallengeDP {
    interface ChallengeDPCallbacks{
        void onSuccess(ArrayList<Challenge> challenges);
        void onError();
    }
    void  getAllScore(ChallengeDPCallbacks callbacks);
}
