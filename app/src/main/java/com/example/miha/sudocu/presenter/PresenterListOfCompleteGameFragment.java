package com.example.miha.sudocu.presenter;

import android.os.Bundle;

import com.example.miha.sudocu.data.DP.IRepository;
import com.example.miha.sudocu.view.IView.IListOfCompleteGameFragment;
import com.example.miha.sudocu.data.DP.ChallengeDP;
import com.example.miha.sudocu.data.DP.ChallengeDpImpl;
import com.example.miha.sudocu.data.DP.IRepositoryUser;
import com.example.miha.sudocu.data.DP.RetroClient;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.model.User;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfCompleteGame;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PresenterListOfCompleteGameFragment implements IPresenterOfCompleteGame {
    private IRepository repository;
    private boolean isRetain = false;
    private ArrayList<Grid> games;
    private static final String SAVE_GAME = "SAVE_GAME_LIST_ON_COMPLETE_GAME_FRAGMENT";
    private static final String IS_RETAIN = "SAVE_IS_RETAIN_FRAGMENT_ON_COMPLETE_GAME_FRAGMENT";
    private ChallengeDP challengeDP = new ChallengeDpImpl(RetroClient.getInstance());
    private IRepositoryUser repositoryUser;
    private IListOfCompleteGameFragment view;

    @Override
    public void init(Bundle bundle) {
        if (bundle != null) {
            ArrayList<Grid> localGrid = (ArrayList<Grid>) bundle.getSerializable(SAVE_GAME);
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

    public PresenterListOfCompleteGameFragment(IRepository repository,IRepositoryUser repositoryUser) {
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