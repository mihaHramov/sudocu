package com.example.miha.sudocu;

import android.app.Application;

import com.example.miha.sudocu.di.component.AppComponent;
import com.example.miha.sudocu.di.component.DaggerAppComponent;
import com.example.miha.sudocu.di.module.AppModule;


public class App extends Application {
    static AppComponent component;

    public static AppComponent getComponent(){
        return component;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        component =  DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}