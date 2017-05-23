package com.example.miha.sudocu.presenter.IPresenter;

public interface IPresenterOfCompleteGame extends IPresenterOfFragment{
    void deleteGame(int id);
    void sendGame(int id);
}
