package com.example.miha.sudocu.mvp.presenter;

import android.os.Bundle;

import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterOfNonCompleteGame;
import com.example.miha.sudocu.mvp.view.intf.IListOfNotCompletedGameFragment;
import com.example.miha.sudocu.mvp.data.model.Grid;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Scheduler;


public class PresenterListOfGameFragment implements IPresenterOfNonCompleteGame {
    private IRepositoryGame repository;
    private static final String GAMES = "PRESENTER_SAVE_GAMES_IN_NON_COMPLETE_GAME";
    private IListOfNotCompletedGameFragment view;
    private ArrayList<Grid> games;
    @Inject
    @Named("main")
    Scheduler mainScheduler;
    @Inject
    @Named("db")
    Scheduler scheduler;

    @Inject
    public PresenterListOfGameFragment(IRepositoryGame repository) {
        this.repository = repository;
    }

    @Override
    public void savePresenterData(Bundle bundle) {
        bundle.putSerializable(GAMES, games);
    }

    @Override
    public void init(Bundle bundle) {
        if (bundle != null) {
            games = (ArrayList<Grid>) bundle.getSerializable(GAMES);
        }
    }

    @Override
    public void onResume() {
        repository.getListGames()
                .subscribeOn(scheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe(() -> view.showLoad(true))
                .doOnCompleted(() -> view.showLoad(false)).subscribe(grids -> {
            view.refreshListOfCompleteGame(grids);
            games = grids;
        });
    }

    @Override
    public void setView(IListOfNotCompletedGameFragment view) {
        this.view = view;
    }
}