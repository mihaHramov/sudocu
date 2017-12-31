package com.example.miha.sudocu.presenter;

import com.example.miha.sudocu.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterMainActivity;
import com.example.miha.sudocu.view.intf.IMainActivity;


public class PresenterMainActivity implements IPresenterMainActivity {
    private IMainActivity view;
    private IRepositorySettings settings;
    public PresenterMainActivity(IRepositorySettings repositorySettings) {
        settings = repositorySettings;
    }

    @Override
    public void setView(IMainActivity activity) {
        view = activity;
    }

    @Override
    public void unSubscription() {
        view = null;
    }

    @Override
    public void isPortrait(Boolean isPortrait) {
        //проверить предыдущие настройки
        Boolean showKeyOnTheRight = !settings.getKeyboardMode();
        if ((!isPortrait && showKeyOnTheRight)||isPortrait) {
            view.showTheKeyboardOnTheRightSide();
        }else {
            view.showTheKeyboardOnTheLeftSide();
        }
    }
}
