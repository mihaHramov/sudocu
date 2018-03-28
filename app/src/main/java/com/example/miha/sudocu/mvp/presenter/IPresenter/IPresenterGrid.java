package com.example.miha.sudocu.mvp.presenter.IPresenter;


import com.example.miha.sudocu.mvp.data.model.Grid;

public interface IPresenterGrid {
    void setModel(Grid gr);

    void clearError();

    void updateUI();

    void choseInput(int id);

    void onResume();

    void deleteAnswer();
}
