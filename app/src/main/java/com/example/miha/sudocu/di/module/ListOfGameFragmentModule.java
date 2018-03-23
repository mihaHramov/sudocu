package com.example.miha.sudocu.di.module;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.data.DP.intf.ChallengeDP;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryUser;
import com.example.miha.sudocu.mvp.presenter.PresenterListOfCompleteGameFragment;
import com.example.miha.sudocu.mvp.view.adapter.AdapterGrid;


import dagger.Module;
import dagger.Provides;

@Module
public class ListOfGameFragmentModule {

    @Provides
    public AdapterGrid provideAdapterGrid() {
        return new AdapterGrid(R.layout.item);
    }

    @Provides
    public PresenterListOfCompleteGameFragment providePresenterListOfCompleteGameFragment(IRepositoryGame repositoryGame, IRepositoryUser user, ChallengeDP dp) {
        return new PresenterListOfCompleteGameFragment(repositoryGame, user, dp);
    }
}
