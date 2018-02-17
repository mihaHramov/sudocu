package com.example.miha.sudocu.di.component;


import com.example.miha.sudocu.mvp.view.fragment.SettingsFragment;


import dagger.Subcomponent;

@Subcomponent
public interface SettingsComponent {
    void inject(SettingsFragment fragment);
}