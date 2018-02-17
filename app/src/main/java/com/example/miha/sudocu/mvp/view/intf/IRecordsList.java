package com.example.miha.sudocu.mvp.view.intf;


import com.example.miha.sudocu.mvp.data.model.Challenge;

import java.util.ArrayList;

public interface IRecordsList {
    void choiceChallenge(Challenge challenge);
    void showRecords(ArrayList<Challenge> challenges);
}
