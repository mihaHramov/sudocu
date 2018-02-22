package com.example.miha.sudocu.di.module;


import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
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
                                                       IRepositoryGame game,
                                                       Scheduler newSche,
                                                       @Named("main") Scheduler main,
                                                       @Named("db") Scheduler db) {

        PresenterMainActivity mainActivityPresenter = new PresenterMainActivity(repositorySettings, game);
        mainActivityPresenter.setSchedulers(db, main, newSche);
        return mainActivityPresenter;
    }
}