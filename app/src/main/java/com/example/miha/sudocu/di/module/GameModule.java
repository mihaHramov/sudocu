package com.example.miha.sudocu.di.module;

import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.mvp.presenter.PresenterGrid;
import com.example.miha.sudocu.mvp.presenter.PresenterKeyboard;
import com.example.miha.sudocu.mvp.presenter.interactor.impl.PresenterGridInteractor;
import com.example.miha.sudocu.mvp.presenter.interactor.intf.IPresenterGridInteractor;


import dagger.Module;
import dagger.Provides;

@Module
public class GameModule {
    @Provides
    public IPresenterGridInteractor providePresenterGridInteractor(IRepositorySettings settings){
        return new PresenterGridInteractor(settings);
    }
    @Provides
    public PresenterKeyboard providePresenterKeyboard(IRepositorySettings settings){
       return new PresenterKeyboard(settings);
    }
    @Provides
    public IPresenterGrid providePresenterOfGrid(IPresenterGridInteractor interactor){
     return   new PresenterGrid(interactor);
    }
}