package com.example.miha.sudocu.di.component;

import com.example.miha.sudocu.di.module.AppModule;
import com.example.miha.sudocu.di.module.ChallengeModule;
import com.example.miha.sudocu.di.module.NetWorkModule;
import com.example.miha.sudocu.di.module.RegistrationModule;
import com.example.miha.sudocu.di.module.RepositoryModule;
import com.example.miha.sudocu.di.module.SchedulersModule;
import com.example.miha.sudocu.mvp.view.activity.MainActivity;
import com.example.miha.sudocu.mvp.view.fragment.RecordsListFragment;
import com.example.miha.sudocu.mvp.view.fragment.RegistrationFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ChallengeModule.class, SchedulersModule.class, NetWorkModule.class, RepositoryModule.class, RegistrationModule.class})
public interface AppComponent {
    SettingsComponent settingsComponent();
    PlayingComponent playingFragment();
    GameListComponent gameListComponent();
    PlayingActivityComponent playingComponent();
    RatingComponent ratingComponent();
    void inject(RegistrationFragment fragment);
}