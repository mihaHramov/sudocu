package com.example.miha.sudocu.mvp.presenter.IPresenter;

import com.example.miha.sudocu.mvp.data.model.Grid;

public interface IPresenterOfCompleteGame extends IPresenterOfFragment{
    void deleteGame(Grid grid);
    void sendGame(Grid grid);
}
