package com.example.miha.sudocu.di.module;

import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.mvp.presenter.PresenterGrid;

import dagger.Module;
import dagger.Provides;

@Module
public class GameModule {
    @Provides
    public IPresenterGrid providePresenterOfGrid(IRepositoryGame repositoryGame, IRepositorySettings settings){
        return new PresenterGrid(repositoryGame,settings);
    }
}
