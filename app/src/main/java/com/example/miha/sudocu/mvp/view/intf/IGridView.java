package com.example.miha.sudocu.mvp.view.intf;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.Pair;
import com.example.miha.sudocu.mvp.data.model.Answer;

import java.util.List;
import java.util.Map;

public interface IGridView extends MvpView {

    void showGrid(Answer[][] ar);

    void showError(List<Answer> list);

    void clearField(List<Integer> theSameAnswers);

    void setFocus(Pair<Integer,Integer> pair);

    void removeFocus(Integer id);

    void setTextToAnswer(Answer answer);

    void showCountOfAnswer(Map<String, String> count);

    void disableButtonHistoryButton(Boolean back,Boolean forward);

    void gameOver();

    void colorThePlayingField(List<Pair<Integer,Integer>> pairs);
}
