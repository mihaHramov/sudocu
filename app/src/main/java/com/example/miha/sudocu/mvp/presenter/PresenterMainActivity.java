package com.example.miha.sudocu.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterMainActivity;
import com.example.miha.sudocu.mvp.view.intf.IMainActivity;
import com.example.miha.sudocu.utils.ConverterTime;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;


@InjectViewState
public class PresenterMainActivity extends MvpPresenter<IMainActivity> implements IPresenterMainActivity {
    private IRepositorySettings settings;
    private IRepositoryGame repository;
    private Scheduler dbScheduler;
    private Subscription subscription;
    private Grid model;
    private Scheduler newScheduler;
    private Scheduler mainScheduler;

    @Override
    public void reloadGame() {
        model.reloadGame();
    }

    @Override
    public void isGameOver() {
        if (model.isGameOver()) {
            getViewState().gameOver();
        }
    }

    @Override
    public void replayGame() {
        model.replayGame();
        getViewState().updateGameUI();
    }

    @Override
    public void setSchedulers(Scheduler db, Scheduler main,Scheduler newSche) {
        newScheduler = newSche;
        dbScheduler = db;
        mainScheduler = main;
    }

    public PresenterMainActivity(IRepositorySettings repositorySettings,IRepositoryGame game) {
        settings = repositorySettings;
        repository = game;
    }


    @Override
    public void onPause() {
        repository.saveGame(model)
                .subscribeOn(dbScheduler)
                .observeOn(mainScheduler)
                .subscribe(aVoid -> {
                }, throwable -> Log.d("mihaHramov", throwable.getMessage()), () -> {});
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
        if ((!isPortrait && showKeyOnTheRight) || isPortrait) {
            getViewState().showTheKeyboardOnTheRightSide();
        } else {
            getViewState().showTheKeyboardOnTheLeftSide();
        }
        if (subscription == null || subscription.isUnsubscribed()) {
            subscription = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(newScheduler)
                    .observeOn(mainScheduler)
                    .subscribe(aLong -> {
                        ConverterTime converterTime = ConverterTime.getInstance();
                        Long minutes = converterTime.converterLongToMinutes(model.getGameTime());
                        Long second = converterTime.converterLongToSeconds(model.getGameTime());
                        getViewState().changeSubTitleToolbar(minutes + ":" + second);
                        model.setGameTime(model.getGameTime() + 1);
                    });
        }
        getViewState().changeTitleToolbar(model.getName());
    }
}