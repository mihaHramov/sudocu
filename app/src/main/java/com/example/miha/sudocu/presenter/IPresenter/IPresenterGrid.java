package com.example.miha.sudocu.presenter.IPresenter;


public interface IPresenterGrid {

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
