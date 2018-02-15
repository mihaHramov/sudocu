package com.example.miha.sudocu.di.module;


import android.content.Context;

import com.example.miha.sudocu.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final App aplication;
    public AppModule(App app){
        aplication = app;
    }

    @Provides
    @Singleton
    public Context provideContext(){
       return aplication.getApplicationContext();
    }

}
