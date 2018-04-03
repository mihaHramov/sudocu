package com.example.miha.sudocu.mvp.presenter.interactor.intf;


import com.example.miha.sudocu.mvp.data.model.Answer;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.data.model.HistoryAnswer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface IPresenterGridInteractor {
    Boolean isChoiceFieldNotSameAnswer(String str);

    Boolean isChoiceFieldNotKnowByDefault(Integer id);

    void deleteChoiceAnswer(Integer id);

    void setChoiceField(Integer id);

    List<Integer> sameAnswerMode(Integer id, ArrayList<Integer> error);

    List<Integer> knowAnswerMode(Integer id, ArrayList<Integer> error);

    ArrayList<Integer> getError();

    void setModel(Grid grid);

    Map<String, String> getCountOfAnswer();

    Integer getChoiceField();

    Boolean isTopOfHistory();

    Boolean isBottomOfHistory();

    HistoryAnswer historyPrev();

    HistoryAnswer historyNext();

    Boolean isGameOver();

    void addNewAnswer(Answer answer);

    Answer[][] getGameField();

    HistoryAnswer getCurrentHistoryAnswer();
}
