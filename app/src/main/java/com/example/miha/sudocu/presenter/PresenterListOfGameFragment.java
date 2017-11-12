package com.example.miha.sudocu.presenter;

import android.os.Bundle;

import com.example.miha.sudocu.data.DP.IRepository;
import com.example.miha.sudocu.view.IView.IListOfNotCompletedGameFragment;
import com.example.miha.sudocu.data.model.Grid;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PresenterListOfGameFragment implements IPresenterOfNonCompleteGame {
    private IRepository repository;
    private static final String GAMES = "PRESENTER_SAVE_GAMES_IN_NON_COMPLETE_GAME";
    private IListOfNotCompletedGameFragment view;
    private ArrayList<Grid> games;

    public PresenterListOfGameFragment(IRepository repository) {
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
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> view.showLoad(true))
                .doOnCompleted(()->view.showLoad(false)).subscribe(grids ->{
                    view.refreshListOfCompleteGame(grids);
                    games = grids;});
    }

    @Override
    public void setView(IListOfNotCompletedGameFragment view) {
        this.view = view;
    }
}
