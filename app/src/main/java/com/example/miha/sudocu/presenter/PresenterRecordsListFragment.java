package com.example.miha.sudocu.presenter;

import com.example.miha.sudocu.data.DP.ChallengeApi;
import com.example.miha.sudocu.data.DP.ChallengeDP;
import com.example.miha.sudocu.data.DP.ChallengeDpImpl;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfFragment;


public class PresenterRecordsListFragment implements IPresenterOfFragment{
    private ChallengeDP challengeDP;

    public PresenterRecordsListFragment(ChallengeApi api){
        challengeDP =  new ChallengeDpImpl(api);
    }
    @Override
    public void onResume() {

    }

}
