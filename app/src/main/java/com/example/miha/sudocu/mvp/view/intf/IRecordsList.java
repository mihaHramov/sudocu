package com.example.miha.sudocu.mvp.view.intf;


import com.arellomobile.mvp.MvpView;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.data.model.LocalChallenge;

import java.util.List;


public interface IRecordsList extends MvpView {
    void choiceChallenge(Grid challenge);
    void showRecords(List<LocalChallenge> challenges);
    void showLoading(Boolean flag);
    void dontStartCompleteGame();
}
