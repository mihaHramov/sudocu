package com.example.miha.sudocu.di.module;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Provides
    RecyclerView.LayoutManager provideLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }
}
