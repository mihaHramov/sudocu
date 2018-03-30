package com.example.miha.sudocu.di.component;


import com.example.miha.sudocu.di.module.ListOfGameFragmentModule;
import com.example.miha.sudocu.mvp.presenter.PresenterListOfCompleteGameFragment;
import com.example.miha.sudocu.mvp.presenter.PresenterListOfGameFragment;
import com.example.miha.sudocu.mvp.view.fragment.ListOfCompleteGameFragment;
import com.example.miha.sudocu.mvp.view.fragment.ListOfGameFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {ListOfGameFragmentModule.class})
public interface GameListComponent {
    PresenterListOfCompleteGameFragment getPresenterOfCompleteGame();
    PresenterListOfGameFragment getPresenterOfListNonCompleteGame();
    void inject(ListOfGameFragment fragment);
    void inject(ListOfCompleteGameFragment fragment);
}
