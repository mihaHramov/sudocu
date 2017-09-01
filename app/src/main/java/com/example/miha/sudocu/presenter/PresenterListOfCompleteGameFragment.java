package com.example.miha.sudocu.presenter;

import android.content.Context;
import android.os.Bundle;

import com.example.miha.sudocu.view.IView.IListOfCompleteGameFragment;
import com.example.miha.sudocu.data.DP.ChallengeDP;
import com.example.miha.sudocu.data.DP.ChallengeDpImpl;
import com.example.miha.sudocu.data.DP.IRepositoryUser;
import com.example.miha.sudocu.data.DP.RepositoryImplBD;
import com.example.miha.sudocu.data.DP.RepositoryUser;
import com.example.miha.sudocu.data.DP.RetroClient;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.model.User;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfCompleteGame;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PresenterListOfCompleteGameFragment implements IPresenterOfCompleteGame {
    private RepositoryImplBD repository;
    private boolean isRetain = false;
    private ArrayList<Grid> games;
    private static final String SAVE_GAME= "SAVE_GAME_LIST_ON_COMPLETE_GAME_FRAGMENT";
    private static final String IS_RETAIN= "SAVE_IS_RETAIN_FRAGMENT_ON_COMPLETE_GAME_FRAGMENT";
    private ChallengeDP challengeDP = new ChallengeDpImpl(RetroClient.getInstance());
    private IRepositoryUser repositoryUser;
    private IListOfCompleteGameFragment view;

    @Override
    public void init(Bundle bundle) {
        if(bundle!=null){
            ArrayList<Grid> localGrid= (ArrayList<Grid>)bundle.getSerializable(SAVE_GAME);
            if(localGrid!=null){
                view.refreshListOfCompleteGame(localGrid);
            }
            if((Boolean)bundle.getBoolean(IS_RETAIN)!=null){
                isRetain = bundle.getBoolean(IS_RETAIN);
            }
        }
    }

    @Override
    public void savePresenterData(Bundle bundle) {
        bundle.putSerializable(SAVE_GAME,games);
        bundle.putBoolean(IS_RETAIN,true);
    }

    @Override
    public void deleteGame(Grid grid) {
        view.showLoad(true);
        repository.deleteGame(grid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        aVoid -> {},
                        throwable -> view.showLoad(false),
                        () -> {
                            prepate(repository.getListCompleteGames());
                            view.showLoad(false);
                        });
    }

    @Override
    public void sendGame(Grid grid) {
        User user = repositoryUser.getUser();
        if (user != null) {
            challengeDP.sendGame(user,grid)
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

    public PresenterListOfCompleteGameFragment(Context context) {
        repository = new RepositoryImplBD(context);
        repositoryUser = new RepositoryUser(context);
    }

    private Observable<ArrayList<Grid>> prepate(Observable<ArrayList<Grid>> gr) {
        gr.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> view.showLoad(true))
                .doOnCompleted(() -> view.showLoad(false))
                .subscribe(grids -> {
                    games = grids;
                    view.refreshListOfCompleteGame(games);
                });
        return gr;
    }

    @Override
    public void onResume() {
        if(!isRetain){
            prepate(repository.getListCompleteGames());
        }
    }
}