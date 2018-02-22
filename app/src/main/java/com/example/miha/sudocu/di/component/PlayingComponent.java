package com.example.miha.sudocu.di.component;

import com.example.miha.sudocu.di.module.GameModule;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.mvp.presenter.PresenterKeyboard;
import com.example.miha.sudocu.mvp.view.fragment.KeyBoardFragment;
import com.example.miha.sudocu.mvp.view.fragment.PlayingFieldFragment;

import dagger.Subcomponent;
@Subcomponent(modules = GameModule.class)
public interface PlayingComponent {
    IPresenterGrid getPresenter();
    PresenterKeyboard getPresenterKeyboard();
    void inject(PlayingFieldFragment fragment);
    void inject(KeyBoardFragment fragment);
}
