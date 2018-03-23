package com.example.miha.sudocu.mvp.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterOfFragment;
import com.example.miha.sudocu.mvp.view.intf.IListOfNotCompletedGameFragment;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Scheduler;

@InjectViewState
public class PresenterListOfGameFragment extends MvpPresenter<IListOfNotCompletedGameFragment> implements IPresenterOfFragment {
    private IRepositoryGame repository;
    @Inject
    @Named("main")
    Scheduler mainScheduler;
    @Inject
    @Named("db")
    Scheduler scheduler;

    @Inject
    public PresenterListOfGameFragment(IRepositoryGame repository) {
        this.repository = repository;
    }


    @Override
    public void onResume() {
        repository.getListGames()
                .subscribeOn(scheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe(() -> getViewState().showLoad(true))
                .doOnCompleted(() -> getViewState().showLoad(false)).subscribe(grids -> getViewState().refreshListOfCompleteGame(grids));
    }
}