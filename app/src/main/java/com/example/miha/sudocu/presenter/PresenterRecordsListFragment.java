package com.example.miha.sudocu.presenter;


import android.app.Activity;
import android.widget.ListView;

import com.example.miha.sudocu.data.DP.ChallengeDP;
import com.example.miha.sudocu.data.DP.ChallengeDpImpl;
import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.presenter.Adapter.AdapterChallenge;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfFragment;

import java.util.ArrayList;

public class PresenterRecordsListFragment implements IPresenterOfFragment{
    private AdapterChallenge adapter;
    private ChallengeDP challengeDP = new ChallengeDpImpl();
    private ChallengeDP.ChallengeDPGetAllScoreCallbacks challengeDPCallbacks = new ChallengeDP.ChallengeDPGetAllScoreCallbacks() {
        @Override
        public void onSuccess(ArrayList<Challenge> challenges) {
            adapter.setData(challenges);
        }

        @Override
        public void onError() {

        }
    };

    @Override
    public void onResume() {

    }

    @Override
    public void initListView(ListView listView) {
        listView.setAdapter(adapter);
    }
    public PresenterRecordsListFragment(Activity activity){
        adapter = new AdapterChallenge(activity);
        challengeDP.getAllScore(challengeDPCallbacks);
    }
}
