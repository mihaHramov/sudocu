package com.example.miha.sudocu.di.module;

import android.content.Context;

import com.example.miha.sudocu.mvp.data.DP.RepositoryGameImplBD;
import com.example.miha.sudocu.mvp.data.DP.RepositorySettings;
import com.example.miha.sudocu.mvp.data.DP.RepositoryUser;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryUser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class RepositoryModule {
    @Provides
    @Singleton
    public IRepositorySettings provideRepositorySettings(Context context){
        return new RepositorySettings(context);
    }

    @Provides
    @Singleton
    public IRepositoryGame provideRepositoryGame(Context context){
        return  new RepositoryGameImplBD(context);
    }

    @Provides
    @Singleton
    public IRepositoryUser provideRepositoryUser(Context context){
        return new RepositoryUser(context);
    }
}
