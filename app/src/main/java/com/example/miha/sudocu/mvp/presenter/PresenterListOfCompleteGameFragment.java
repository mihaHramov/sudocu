package com.example.miha.sudocu.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.view.intf.IListOfCompleteGameFragment;
import com.example.miha.sudocu.mvp.data.DP.intf.ChallengeDP;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryUser;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.data.model.User;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterOfCompleteGame;


import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class PresenterListOfCompleteGameFragment extends MvpPresenter<IListOfCompleteGameFragment> implements IPresenterOfCompleteGame {
    private IRepositoryGame repository;
    private ArrayList<Grid> localGames;
    private ChallengeDP challengeDP;
    private IRepositoryUser repositoryUser;


    private Grid localSendGame;
    @Override
    public void deleteGame(Grid grid) {
        repository.deleteGame(grid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnCompleted(() -> getViewState().showLoad(false))
                .doOnSubscribe(() -> getViewState().showLoad(true))
                .subscribe(aVoid -> {},
                        throwable -> getViewState().showLoad(false),
                        () -> getViewState().deleteGameFromList());
    }

    @Override
    public void sendGame(Grid grid) {
        User user = repositoryUser.getUser();
        localSendGame = grid;
        if (user != null) {
            challengeDP.sendGame(user, localSendGame)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aVoid -> getViewState().onSendGame(), throwable -> getViewState().onErrorSendGame(),()->localSendGame = null);
        } else {
            getViewState().authUser();
        }
    }

    public PresenterListOfCompleteGameFragment(IRepositoryGame repository, IRepositoryUser repositoryUser, ChallengeDP dp) {
        this.challengeDP = dp;
        this.repository = repository;
        this.repositoryUser = repositoryUser;
    }

    @Override
    public void onResume() {
        if (localGames != null) {
            return;
        }
        repository.getListCompleteGames()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getViewState().showLoad(true))
                .doOnCompleted(() -> getViewState().showLoad(false))
                .subscribe(grids -> {
                    localGames = grids;
                    getViewState().refreshListOfCompleteGame(grids);
                });
    }
}