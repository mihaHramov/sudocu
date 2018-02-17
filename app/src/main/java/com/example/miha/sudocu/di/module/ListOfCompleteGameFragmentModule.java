package com.example.miha.sudocu.di.module;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.api.ChallengeApi;
import com.example.miha.sudocu.data.DP.ChallengeDpImpl;
import com.example.miha.sudocu.data.DP.intf.ChallengeDP;
import com.example.miha.sudocu.presenter.Adapter.AdapterGrid;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ListOfCompleteGameFragmentModule {

    @Provides
    @Singleton
    public ChallengeDP provideChalleng(ChallengeApi api){
        return new ChallengeDpImpl(api);
    }

    @Provides
    public AdapterGrid  provideAdapterGrid(){
        return  new AdapterGrid(R.layout.item);
    }
}
