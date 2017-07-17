package com.example.miha.sudocu.presenter.IPresenter;

import com.example.miha.sudocu.View.IView.IListOfCompleteGameFragment;
import com.example.miha.sudocu.data.model.Grid;

public interface IPresenterOfCompleteGame extends IPresenterOfFragment{
    void deleteGame(Grid grid);
    void sendGame(Grid grid);
    void setView(IListOfCompleteGameFragment view);
}
