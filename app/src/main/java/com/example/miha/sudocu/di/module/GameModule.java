package com.example.miha.sudocu.di.module;

import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.mvp.presenter.PresenterGrid;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class GameModule {
    @Provides
    public IPresenterGrid providePresenterOfGrid(IRepositoryGame repositoryGame, IRepositorySettings settings, @Named("db") Scheduler db, @Named("main") Scheduler main, Scheduler all){
     PresenterGrid presenterGrid =  new PresenterGrid(repositoryGame,settings);
     presenterGrid.setScheduler(db,main,all);
     return   presenterGrid;
    }
}