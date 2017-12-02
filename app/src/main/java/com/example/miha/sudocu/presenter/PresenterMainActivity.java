package com.example.miha.sudocu.presenter;

import com.example.miha.sudocu.presenter.IPresenter.IPresenterMainActivity;
import com.example.miha.sudocu.view.IView.IMainActivity;


public class PresenterMainActivity implements IPresenterMainActivity {
    private IMainActivity view;

    public PresenterMainActivity(IMainActivity activity) {
        view = activity;
    }

    @Override
    public void unSubscription() {
        view = null;
    }

    @Override
    public void isPortrait(Boolean isPortrait) {
        //проверить предыдущие настройки
        Boolean showKeyOnTheRight = false;
        if ((!isPortrait && showKeyOnTheRight)||isPortrait) {
            view.showTheKeyboardOnTheRightSide();
        }else {
            view.showTheKeyboardOnTheLeftSide();
        }
    }
}
