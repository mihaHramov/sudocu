package com.example.miha.sudocu.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.DP.intf.ChallengeDP;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.data.model.Challenge;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterOfRecordsList;
import com.example.miha.sudocu.mvp.view.intf.IRecordsList;


import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class PresenterRecordsListFragment extends MvpPresenter<IRecordsList> implements IPresenterOfRecordsList {
    private ChallengeDP challengeDP;
    public IRepositoryGame repositoryGame;


    public PresenterRecordsListFragment(ChallengeDP dp, IRepositoryGame repository) {
        this.challengeDP = dp;
        this.repositoryGame = repository;
    }

    @Override
    public void choiceChallenge(Challenge challenge) {
        challenge.getGrid().setId(0);
        challenge.getGrid().replayGame();
        repositoryGame
                .saveGame(challenge.getGrid())
                .flatMap(integer -> repositoryGame.saveChallenge(challenge.getLogin(), integer))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> getViewState().choiceChallenge(challenge));
    }


    @Override
    public void onResume() {
        challengeDP.getAllScore()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(challenges -> getViewState().showRecords(challenges), throwable -> Log.d("mihaHramov", throwable.getMessage()));
    }
}
