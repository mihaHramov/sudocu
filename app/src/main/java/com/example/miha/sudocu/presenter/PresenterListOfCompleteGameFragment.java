package com.example.miha.sudocu.presenter;

import android.os.Bundle;

import com.example.miha.sudocu.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.view.intf.IListOfCompleteGameFragment;
import com.example.miha.sudocu.data.DP.intf.ChallengeDP;
import com.example.miha.sudocu.data.DP.intf.IRepositoryUser;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.model.User;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfCompleteGame;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PresenterListOfCompleteGameFragment implements IPresenterOfCompleteGame {
    private IRepositoryGame repository;
    private boolean isRetain = false;
    private ArrayList<Grid> games;
    private static final String SAVE_GAME = "SAVE_GAME_LIST_ON_COMPLETE_GAME_FRAGMENT";
    private static final String IS_RETAIN = "SAVE_IS_RETAIN_FRAGMENT_ON_COMPLETE_GAME_FRAGMENT";
    private ChallengeDP challengeDP ;
    private IRepositoryUser repositoryUser;
    private IListOfCompleteGameFragment view;

    @Override
    public void init(Bundle bundle) {
        if (bundle != null) {
            ArrayList<Grid> localGrid =  (ArrayList<Grid>) bundle.getSerializable(SAVE_GAME);
            if (localGrid != null) {
                view.refreshListOfCompleteGame(localGrid);
            }
            if (bundle.getBoolean(IS_RETAIN)) {
                isRetain = bundle.getBoolean(IS_RETAIN);
            }
        }
    }

    @Override
    public void savePresenterData(Bundle bundle) {
        bundle.putSerializable(SAVE_GAME, games);
        bundle.putBoolean(IS_RETAIN, true);
    }

    @Override
    public void deleteGame(Grid grid) {
        repository.deleteGame(grid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnCompleted(() -> view.showLoad(false))
                .doOnSubscribe(() -> view.showLoad(true))
                .subscribe(aVoid -> {}, throwable -> view.showLoad(false), () -> view.deleteGameFromList());
    }

    @Override
    public void sendGame(Grid grid) {
        User user = repositoryUser.getUser();
        if (user != null) {
            challengeDP.sendGame(user, grid)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aVoid -> view.onSendGame(), throwable -> view.onErrorSendGame());
        } else {
            view.authUser();
        }
    }

    @Override
    public void setView(IListOfCompleteGameFragment view) {
        this.view = view;
    }

    @Inject
    public PresenterListOfCompleteGameFragment(IRepositoryGame repository, IRepositoryUser repositoryUser,ChallengeDP dp) {
        this.challengeDP = dp;
        this.repository = repository;
        this.repositoryUser = repositoryUser;
    }

    @Override
    public void onResume() {
        if (!isRetain) {
            repository.getListCompleteGames().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(() -> view.showLoad(true))
                    .doOnCompleted(() -> view.showLoad(false))
                    .subscribe(grids -> {
                        games = grids;
                        view.refreshListOfCompleteGame(games);
                    });
        }
    }
}