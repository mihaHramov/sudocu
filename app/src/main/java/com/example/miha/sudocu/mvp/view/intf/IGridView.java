package com.example.miha.sudocu.mvp.view.intf;

import com.arellomobile.mvp.MvpView;
import com.example.miha.sudocu.mvp.data.model.Answer;

import java.util.ArrayList;
import java.util.Map;

public interface IGridView extends MvpView {

    void showGrid(Answer[][] ar);

    void showTheSameAnswers(ArrayList<Integer> listId);

    void showError(ArrayList<Integer> list);

    void clearError(ArrayList<Integer> list);

    void showKnownOptions(ArrayList<Integer> list);

    void clearTheSameAnswer(ArrayList<Integer> theSameAnswers);

    void clearKnownOptions(ArrayList<Integer> list);

    void gameOver();

    void setGameTime(long time);

    void setFocus(Integer id,Boolean isError);

    void removeFocus(Integer id);

    void setTextToAnswer(Answer answer);

    void setGameName(String name);

    void showCountOfAnswer(Map<String, Integer> count);

    void clearCountOfAnswer();
}
