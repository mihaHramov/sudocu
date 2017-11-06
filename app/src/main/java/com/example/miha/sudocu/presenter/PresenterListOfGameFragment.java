package com.example.miha.sudocu.presenter;

import android.content.Context;
import android.os.Bundle;

import com.example.miha.sudocu.view.IView.IListOfNotCompletedGameFragment;
import com.example.miha.sudocu.data.DP.RepositoryImplBD;
import com.example.miha.sudocu.data.model.Grid;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PresenterListOfGameFragment implements IPresenterOfNonCompleteGame {
    private RepositoryImplBD repository;
    private static final String GAMES = "PRESENTER_SAVE_GAMES_IN_NON_COMPLETE_GAME";
    private IListOfNotCompletedGameFragment view;
    private ArrayList<Grid> games;

    public PresenterListOfGameFragment(Context context) {
        repository = new RepositoryImplBD(context);
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

    private Observable<ArrayList<Grid>> prepate(Observable<ArrayList<Grid>> gr) {
        gr.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> view.showLoad(true))
                .doOnCompleted(() -> view.showLoad(false))
                .subscribe(grids -> {
                    view.refreshListOfCompleteGame(grids);
                    games = grids;
                });
        return gr;
    }

    @Override
    public void onResume() {
        prepate(repository.getListGames());
    }

    @Override
    public void setView(IListOfNotCompletedGameFragment view) {
        this.view = view;
    }
}
