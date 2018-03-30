package com.example.miha.sudocu.mvp.view.intf;


import com.arellomobile.mvp.MvpView;

public interface IMainActivity extends MvpView {
    void gameOver();
    void changeTitleToolbar(String str);
    void changeSubTitleToolbar(String str);
    void showTheKeyboardOnTheLeftSide(Boolean flag);
    void updateGameUI();
}