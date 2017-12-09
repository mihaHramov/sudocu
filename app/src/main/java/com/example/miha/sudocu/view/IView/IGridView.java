package com.example.miha.sudocu.view.IView;

import com.example.miha.sudocu.data.model.Answer;

import java.util.ArrayList;

public interface IGridView {
    void showErrorFocus(int id);

    void showGrid(Answer[][] ar);

    void showTheSameAnswers(ArrayList<Integer> listId);

    void showError(ArrayList<Integer> list);

    void clearError(ArrayList<Integer> list);

    void showKnownOptions(ArrayList<Integer> list);

    void clearTheSameAnswer(ArrayList<Integer> theSameAnswers);

    void clearKnownOptions(ArrayList<Integer> list);

    void gameOver();

    void setGameTime(long time);

    void setFocus(Integer id);

    void removeFocus(Integer id);

    void setTextToAnswer(Integer id, String answer);

    void setGameName(String name);
}
