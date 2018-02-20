package com.example.miha.sudocu.di.module;


import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.presenter.PresenterMainActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class PlayingActivityModule {
    @Provides
    PresenterMainActivity providePresenterMainActivity(IRepositorySettings repositorySettings,
                                                       Scheduler db,
                                                       @Named("main") Scheduler main) {
        PresenterMainActivity mainActivityPresenter = new PresenterMainActivity(repositorySettings);
        mainActivityPresenter.setSchedulers(db, main);
        return mainActivityPresenter;
    }
}