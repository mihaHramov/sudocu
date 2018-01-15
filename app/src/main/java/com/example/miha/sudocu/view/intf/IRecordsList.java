package com.example.miha.sudocu.view.intf;


import com.example.miha.sudocu.data.model.Challenge;

import java.util.ArrayList;

public interface IRecordsList {
    void choiceChallenge(Challenge challenge);
    void showRecords(ArrayList<Challenge> challenges);
}
