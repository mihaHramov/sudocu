package com.example.miha.sudocu.di.component;

import com.example.miha.sudocu.di.module.GameModule;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.mvp.view.fragment.PlayingFieldFragment;

import dagger.Subcomponent;

@Subcomponent(modules = GameModule.class)
public interface PlayingComponent {
    IPresenterGrid getPresenter();
    void inject(PlayingFieldFragment fragment);
}
