package com.example.miha.sudocu.di.component;


import com.example.miha.sudocu.view.fragment.SettingsFragment;


import dagger.Subcomponent;

@Subcomponent
public interface SettingsComponent {
    void inject(SettingsFragment fragment);
}