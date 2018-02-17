package com.example.miha.sudocu.mvp.presenter;

import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterMainActivity;
import com.example.miha.sudocu.mvp.view.intf.IMainActivity;

import javax.inject.Inject;


public class PresenterMainActivity implements IPresenterMainActivity {
    private IMainActivity view;
    private IRepositorySettings settings;
    @Inject
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
