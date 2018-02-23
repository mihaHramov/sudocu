package com.example.miha.sudocu.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.DP.intf.ChallengeDP;
import com.example.miha.sudocu.mvp.data.model.Challenge;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterOfRecordsList;
import com.example.miha.sudocu.mvp.view.intf.IRecordsList;


import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class PresenterRecordsListFragment extends MvpPresenter<IRecordsList> implements IPresenterOfRecordsList {
    private ChallengeDP challengeDP;

    @Inject
    public PresenterRecordsListFragment(ChallengeDP dp) {
        this.challengeDP = dp;
    }

    @Override
    public void choiceChallenge(Challenge challenge) {
        getViewState().choiceChallenge(challenge);
    }

    @Override
    public void onResume() {
        challengeDP.getAllScore()
                .cache()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(challenges -> getViewState().showRecords(challenges), throwable -> Log.d("mihaHramov", throwable.getMessage()));
    }
}
