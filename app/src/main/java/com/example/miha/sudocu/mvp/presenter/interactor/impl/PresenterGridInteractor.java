package com.example.miha.sudocu.mvp.presenter.interactor.impl;


import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.presenter.interactor.intf.IPresenterGridInteractor;

import java.util.ArrayList;

import rx.functions.Action1;

public class PresenterGridInteractor implements IPresenterGridInteractor {
    private IRepositorySettings settings;
    private Grid model;

    @Override
    public void sameAnswerMode(Integer id, ArrayList<Integer> error, Action1<ArrayList<Integer>> action1) {
        ArrayList<Integer> sameAnswer = settings.getShowSameAnswerMode() && !model.getAnswer(id).trim().isEmpty() ? model.getTheSameAnswers(id) : new ArrayList<>();
        sameAnswer.removeAll(error);
        action1.call(sameAnswer);
    }

    @Override
    public void knowAnswerMode(Integer id, ArrayList<Integer> error, Action1<ArrayList<Integer>> action1) {
        ArrayList<Integer> knowOptions = settings.getKnowAnswerMode() ? model.getKnowOptions(id) : new ArrayList<>();
        knowOptions.removeAll(error);
        action1.call(knowOptions);
    }

    public PresenterGridInteractor(IRepositorySettings sett) {
        this.settings = sett;
    }

    @Override
    public void setModel(Grid grid) {
        this.model = grid;
    }

    @Override
    public ArrayList<Integer> getError() {
        return settings.getErrorMode() ? model.getErrors() : new ArrayList<>();
    }
}
