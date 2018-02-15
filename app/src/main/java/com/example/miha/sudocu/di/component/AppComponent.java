package com.example.miha.sudocu.di.component;

import com.example.miha.sudocu.di.module.AppModule;
import com.example.miha.sudocu.di.module.NetWorkModule;
import com.example.miha.sudocu.di.module.RepositoryModule;
import com.example.miha.sudocu.view.fragment.SettingsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetWorkModule.class, RepositoryModule.class})
public interface AppComponent {
    void inject(SettingsFragment fragment);
}