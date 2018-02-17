package com.example.miha.sudocu.mvp.presenter.IPresenter;


import com.example.miha.sudocu.mvp.data.model.Grid;

public interface IPresenterGrid {
    void setModel(Grid gr);

    void historyForward();

    void historyBack();

    void reloadGame();

    void replayGame();

    void answer(String answer);

    void choseInput(int id);

    void onResume();

    void onPause();

    void deleteAnswer();
}
