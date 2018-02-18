package com.example.miha.sudocu.di.module;


import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.presenter.PresenterMainActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class PlayingActivityModule {
    @Provides
    PresenterMainActivity providePresenterMainActivity(IRepositorySettings repositorySettings) {
        return new PresenterMainActivity(repositorySettings);
    }
}
