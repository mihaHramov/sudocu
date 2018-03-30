package com.example.miha.sudocu.mvp.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterOfFragment;
import com.example.miha.sudocu.mvp.view.intf.IListOfNotCompletedGameFragment;


import rx.Scheduler;

@InjectViewState
public class PresenterListOfGameFragment extends MvpPresenter<IListOfNotCompletedGameFragment> implements IPresenterOfFragment {
    private IRepositoryGame repository;
    private Scheduler mainScheduler;
    private Scheduler dbScheduler;

    public PresenterListOfGameFragment(IRepositoryGame repository,Scheduler main,Scheduler db) {
        this.dbScheduler = db;
        this.mainScheduler = main;
        this.repository = repository;
    }

    @Override
    public void onResume() {
        repository.getListGames()
                .subscribeOn(dbScheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe(()->getViewState().showLoad(true))
                .doOnCompleted(() -> getViewState().showLoad(false))
                .subscribe(grids -> getViewState().refreshListOfCompleteGame(grids));
    }
}