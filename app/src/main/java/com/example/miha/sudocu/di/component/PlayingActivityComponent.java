package com.example.miha.sudocu.di.component;


import com.example.miha.sudocu.mvp.view.activity.MainActivity;

import dagger.Subcomponent;

@Subcomponent
public interface PlayingActivityComponent {
    void inject(MainActivity mainActivity);
}
