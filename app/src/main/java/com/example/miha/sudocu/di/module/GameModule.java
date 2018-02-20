package com.example.miha.sudocu.di.module;

import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.mvp.presenter.PresenterGrid;
import com.example.miha.sudocu.mvp.presenter.interactor.impl.PresenterGridInteractor;
import com.example.miha.sudocu.mvp.presenter.interactor.intf.IPresenterGridInteractor;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class GameModule {
    @Provides
    public IPresenterGridInteractor providePresenterGridInteractor(IRepositorySettings settings){
        return new PresenterGridInteractor(settings);
    }

    @Provides
    public IPresenterGrid providePresenterOfGrid(IRepositoryGame repositoryGame,
                                                 IRepositorySettings settings,
                                                 IPresenterGridInteractor interactor,
                                                 @Named("db") Scheduler db,
                                                 @Named("main") Scheduler main,
                                                 Scheduler all){
     PresenterGrid presenterGrid =  new PresenterGrid(repositoryGame,interactor,settings);
     presenterGrid.setScheduler(db,main,all);
     return   presenterGrid;
    }
}