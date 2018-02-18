package com.example.miha.sudocu.di.module;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.data.DP.intf.ChallengeDP;
import com.example.miha.sudocu.mvp.presenter.PresenterRecordsListFragment;
import com.example.miha.sudocu.mvp.view.adapter.AdapterChallenge;

import dagger.Module;
import dagger.Provides;

@Module
public class RatingModule {
    @Provides
    public PresenterRecordsListFragment providePresenterRecordsListFragment(ChallengeDP dp) {
        return new PresenterRecordsListFragment(dp);
    }

    @Provides
    public AdapterChallenge provideAdapterChallenge() {
        return new AdapterChallenge(R.layout.item_challenge);
    }
}
