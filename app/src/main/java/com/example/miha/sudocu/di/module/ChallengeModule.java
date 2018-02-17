package com.example.miha.sudocu.di.module;

import com.example.miha.sudocu.api.ChallengeApi;
import com.example.miha.sudocu.mvp.data.DP.ChallengeDpImpl;
import com.example.miha.sudocu.mvp.data.DP.intf.ChallengeDP;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ChallengeModule {
    @Provides
    @Singleton
    public ChallengeDP provideChalleng(ChallengeApi api) {
        return new ChallengeDpImpl(api);
    }
}
