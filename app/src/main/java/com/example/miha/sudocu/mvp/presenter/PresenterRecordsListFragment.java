package com.example.miha.sudocu.mvp.presenter;

import android.os.Bundle;
import android.util.Log;

import com.example.miha.sudocu.mvp.data.DP.intf.ChallengeDP;
import com.example.miha.sudocu.mvp.data.model.Challenge;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterOfRecordsList;
import com.example.miha.sudocu.mvp.view.intf.IRecordsList;


import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PresenterRecordsListFragment implements IPresenterOfRecordsList {
    private ChallengeDP challengeDP;
    private IRecordsList view;

    @Inject
    public PresenterRecordsListFragment(ChallengeDP dp) {
        this.challengeDP =  dp;
    }

    @Override
    public void setView(IRecordsList recordsList) {
        view = recordsList;
    }

    @Override
    public void choiceChallenge(Challenge challenge) {
        view.choiceChallenge(challenge);
    }

    @Override
    public void init(Bundle bundle) {

    }

    @Override
    public void savePresenterData(Bundle bundle) {

    }

    @Override
    public void onResume() {
        challengeDP.getAllScore()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(challenges -> view.showRecords(challenges), throwable -> Log.d("mihaHramov", throwable.getMessage()));
    }
}
