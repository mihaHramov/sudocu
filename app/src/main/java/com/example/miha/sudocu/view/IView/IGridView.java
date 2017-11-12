package com.example.miha.sudocu.view.IView;

import com.example.miha.sudocu.data.model.Answer;

public interface IGridView {
    IGridView showGrid(Answer[][] ar);

    void gameOver();

    void showKnownOptions(int id);

    void setGameTime(long time);

    long getGameTime();

    void focusInput(int id);
}
