package com.example.miha.sudocu.presenter.IPresenter;

import com.example.miha.sudocu.View.IView.IListOfCompleteGameFragment;

public interface IPresenterOfCompleteGame extends IPresenterOfFragment{
    void deleteGame(int id);
    void sendGame(int id);
    void setView(IListOfCompleteGameFragment view);
}
