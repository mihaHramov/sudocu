package com.example.miha.sudocu.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.DP.intf.ChallengeDP;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.data.model.LocalChallenge;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterOfRecordsList;
import com.example.miha.sudocu.mvp.view.intf.IRecordsList;


import rx.Observable;
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
        }else {
            if(localChallenge.getLocalGame().isGameOver()){
                getViewState().dontStartCompleteGame();
            }else {
                getViewState().choiceChallenge(localChallenge.getLocalGame());
            }
        }
    }


    @Override
    public void onResume() {
        challengeDP.getAllScore()
                .flatMap(Observable::from)
                .flatMap(challenges -> repositoryGame.getLocalChallenge(challenges))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(challenges -> getViewState().showRecords(challenges), throwable -> Log.d("mihaHramov", throwable.getMessage()));
    }
}
