package com.example.miha.sudocu.mvp.presenter.IPresenter;


import com.example.miha.sudocu.mvp.data.model.Grid;

public interface IPresenterGrid {
    void setModel(Grid gr);

    void updateUI();

    void choseInput(Integer id);

    void onResume();

    void deleteAnswer();
}
