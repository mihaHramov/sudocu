package com.example.miha.sudocu.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.DP.intf.ChallengeDP;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.data.model.Challenge;
import com.example.miha.sudocu.mvp.data.model.LocalChallenge;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterOfRecordsList;
import com.example.miha.sudocu.mvp.view.intf.IRecordsList;


import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class PresenterRecordsListFragment extends MvpPresenter<IRecordsList> implements IPresenterOfRecordsList {
    private ArrayList<Challenge> localCachChallenge;
    private ChallengeDP challengeDP;
    public IRepositoryGame repositoryGame;


    public PresenterRecordsListFragment(ChallengeDP dp, IRepositoryGame repository) {
        this.repositoryGame = repository;
        this.challengeDP = dp;
    }

    @Override
    public void choiceChallenge(LocalChallenge localChallenge) {
        if (localChallenge.getLocalGame() == null) {
            localChallenge.getChallenge().getGrid().setId(0);
            localChallenge.getChallenge().getGrid().replayGame();
            repositoryGame
                    .saveGame(localChallenge.getChallenge().getGrid())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(integer -> repositoryGame.saveChallenge(localChallenge.getChallenge().getLogin(), integer))
                    .subscribe(integer -> getViewState().choiceChallenge(localChallenge.getChallenge().getGrid()));
        } else {
            if (localChallenge.getLocalGame().isGameOver()) {
                getViewState().dontStartCompleteGame();
            } else {
                getViewState().choiceChallenge(localChallenge.getLocalGame());
            }
        }
    }

    @Override
    public void onResume() {
        Observable.concat(Observable.just(localCachChallenge), challengeDP.getAllScore())
                .filter(challenges -> challenges!=null)
                .filter(challenges -> !challenges.isEmpty())
                .first()
                .doOnNext(challenges -> localCachChallenge = challenges)//local save
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .observeOn(Schedulers.newThread())//переключил поток(все что ниже выплняется в другом потоке)
                .flatMap(challenges -> repositoryGame.getLocalChallenge(challenges))
                .toList()
                .onErrorResumeNext(repositoryGame.getAllLocalChallenge())//load from bd when error
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(localChallenges -> {
                    if (localChallenges.isEmpty()) {
                        //show error when empty list
                    }
                })
                .doOnSubscribe(() -> getViewState().showLoading(true))
                .subscribe(challenges -> getViewState().showRecords(challenges), throwable -> getViewState().showLoading(false), () ->getViewState().showLoading(false));
    }
}