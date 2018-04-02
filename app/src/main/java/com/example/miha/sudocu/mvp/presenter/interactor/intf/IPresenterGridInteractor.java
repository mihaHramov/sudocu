package com.example.miha.sudocu.mvp.presenter.interactor.intf;


import com.example.miha.sudocu.mvp.data.model.Grid;

import java.util.ArrayList;
import java.util.Map;

import rx.functions.Action1;

public interface IPresenterGridInteractor {
    void sameAnswerMode(Integer id, ArrayList<Integer> error, Action1<ArrayList<Integer>> action1);
    void knowAnswerMode(Integer id, ArrayList<Integer> error,Action1<ArrayList<Integer>> action1);
    ArrayList<Integer> getError();
    void setModel(Grid grid);
    Map<String,Integer> getCountOfAnswer();
    Integer getChoiceField();
    Boolean isTopOfHistory();
    Boolean isBottomOfHistory();
}
