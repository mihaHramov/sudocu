package com.example.miha.sudocu.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterMainActivity;
import com.example.miha.sudocu.mvp.view.intf.IMainActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;


@InjectViewState
public class PresenterMainActivity extends MvpPresenter<IMainActivity> implements IPresenterMainActivity {
    private IRepositorySettings settings;
    private Subscription subscription;
    private Grid model;
    private Scheduler newScheduler;
    private Scheduler mainScheduler;

    @Override
    public void setSchedulers(Scheduler db, Scheduler main) {
        newScheduler = db;
        mainScheduler = main;
    }

    public PresenterMainActivity(IRepositorySettings repositorySettings) {
        settings = repositorySettings;
    }

    @Override
    public void onPause() {
        subscription.unsubscribe();
    }

    @Override
    public void setModel(Grid grid) {
        model = grid;
    }

    @Override
    public Grid getModel() {
        return model;
    }

    @Override
    public void isPortrait(Boolean isPortrait) {
        Boolean showKeyOnTheRight = !settings.getKeyboardMode();
        if ((!isPortrait && showKeyOnTheRight)||isPortrait) {
            getViewState().showTheKeyboardOnTheRightSide();
        }else {
            getViewState().showTheKeyboardOnTheLeftSide();
        }
        if (subscription == null || subscription.isUnsubscribed()) {
            subscription = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(newScheduler)
                    .observeOn(mainScheduler)
                    .subscribe(aLong -> {
                        getViewState().changeSubTitleToolbar(Long.toString(model.getGameTime()));
                        model.setGameTime(model.getGameTime() + 1);
                    });
        }
        getViewState().changeTitleToolbar(model.getName());
    }
}