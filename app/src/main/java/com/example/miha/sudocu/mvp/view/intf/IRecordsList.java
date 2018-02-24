package com.example.miha.sudocu.mvp.view.intf;


import com.arellomobile.mvp.MvpView;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.data.model.LocalChallenge;


public interface IRecordsList extends MvpView {
    void choiceChallenge(Grid challenge);
    void showRecords(LocalChallenge challenges);

    void dontStartCompleteGame();
}
