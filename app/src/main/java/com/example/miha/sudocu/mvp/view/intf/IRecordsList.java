package com.example.miha.sudocu.mvp.view.intf;


import com.arellomobile.mvp.MvpView;
import com.example.miha.sudocu.mvp.data.model.Challenge;

import java.util.ArrayList;

public interface IRecordsList extends MvpView {
    void choiceChallenge(Challenge challenge);
    void showRecords(ArrayList<Challenge> challenges);
}
