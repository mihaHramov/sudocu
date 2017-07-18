package com.example.miha.sudocu.presenter;

import android.app.Activity;

import com.example.miha.sudocu.View.IView.IListOfCompleteGameFragment;
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
    private ChallengeDP challengeDP = new ChallengeDpImpl(RetroClient.getInstance());
    private IRepositoryUser repositoryUser;
    private IListOfCompleteGameFragment view;

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

    public PresenterListOfCompleteGameFragment(Activity activity) {
        repository = new RepositoryImplBD(activity);
        repositoryUser = new RepositoryUser(activity);
    }

    private Observable<ArrayList<Grid>> prepate(Observable<ArrayList<Grid>> gr) {
        gr.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> view.showLoad(true))
                .doOnCompleted(() -> view.showLoad(false))
                .subscribe(grids -> view.refreshListOfCompleteGame(grids));
        return gr;
    }

    @Override
    public void onResume() {
        prepate(repository.getListCompleteGames());
    }
}
