package com.example.miha.sudocu.di.component;


import com.example.miha.sudocu.di.module.PlayingActivityModule;
import com.example.miha.sudocu.mvp.presenter.PresenterMainActivity;
import com.example.miha.sudocu.mvp.view.activity.MainActivity;

import dagger.Subcomponent;

@Subcomponent(modules = PlayingActivityModule.class)
public interface PlayingActivityComponent {
    PresenterMainActivity providePresenterMainActivity();
    void inject(MainActivity mainActivity);
}
