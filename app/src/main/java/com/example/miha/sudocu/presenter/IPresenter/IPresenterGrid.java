package com.example.miha.sudocu.presenter.IPresenter;

import android.content.Intent;

import com.example.miha.sudocu.view.IView.IGridView;

public interface IPresenterGrid extends IPresenter {
    void init(Intent intent);

    void reloadGame();

    void answer(String answer);

    void setView(IGridView view);

    void choseInput(int id);

    void onStop();

    void onResume();
}
