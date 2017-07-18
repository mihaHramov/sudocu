package com.example.miha.sudocu.presenter;

import android.app.Activity;

import com.example.miha.sudocu.View.IView.IListOfNotCompletedGameFragment;
import com.example.miha.sudocu.data.DP.RepositoryImplBD;
import com.example.miha.sudocu.data.model.Grid;

import java.util.ArrayList;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PresenterListOfGameFragment implements IPresenterOfNonCompleteGame {
    private RepositoryImplBD repository;
    private IListOfNotCompletedGameFragment view;

    public PresenterListOfGameFragment(Activity activity){
        repository = new RepositoryImplBD(activity);
    }
    private Observable<ArrayList<Grid>> prepate(Observable<ArrayList<Grid>> gr){
        gr.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> view.showLoad(true))
                .doOnCompleted(() ->view.showLoad(false))
                .subscribe(grids -> view.refreshListOfCompleteGame(grids));
        return gr;
    }
    @Override
    public void onResume() {
       prepate(repository.getListGames());
    }

    @Override
    public void setView(IListOfNotCompletedGameFragment view) {
        this.view = view;
    }
}
