package com.example.miha.sudocu.di.component;

import com.example.miha.sudocu.di.module.RatingModule;
import com.example.miha.sudocu.mvp.view.fragment.RecordsListFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {RatingModule.class})
public interface RatingComponent {
    void inject(RecordsListFragment fragment);
}
