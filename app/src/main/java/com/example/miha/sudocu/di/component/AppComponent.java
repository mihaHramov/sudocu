package com.example.miha.sudocu.di.component;

import com.example.miha.sudocu.di.module.AppModule;
import com.example.miha.sudocu.di.module.ListOfCompleteGameFragmentModule;
import com.example.miha.sudocu.di.module.NetWorkModule;
import com.example.miha.sudocu.di.module.RegistrationModule;
import com.example.miha.sudocu.di.module.RepositoryModule;
import com.example.miha.sudocu.di.module.SchedulersModule;
import com.example.miha.sudocu.view.activity.MainActivity;
import com.example.miha.sudocu.view.fragment.ListOfCompleteGameFragment;
import com.example.miha.sudocu.view.fragment.ListOfGameFragment;
import com.example.miha.sudocu.view.fragment.RecordsListFragment;
import com.example.miha.sudocu.view.fragment.RegistrationFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, SchedulersModule.class, NetWorkModule.class, RepositoryModule.class, RegistrationModule.class,ListOfCompleteGameFragmentModule.class})
public interface AppComponent {
    SettingsComponent settingsComponent();
    void inject(ListOfGameFragment fragment);
    void inject(ListOfCompleteGameFragment fragment);
    void inject(RecordsListFragment fragment);
    void inject(MainActivity activity);
    void inject(RegistrationFragment fragment);
}