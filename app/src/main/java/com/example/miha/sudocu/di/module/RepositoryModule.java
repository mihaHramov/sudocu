package com.example.miha.sudocu.di.module;

import android.content.Context;

import com.example.miha.sudocu.data.DP.RepositorySettings;
import com.example.miha.sudocu.data.DP.intf.IRepositorySettings;

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
}
