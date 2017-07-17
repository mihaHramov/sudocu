package com.example.miha.sudocu.presenter;

import android.app.Activity;

import com.example.miha.sudocu.View.IView.IListOfNotCompletedGameFragment;
import com.example.miha.sudocu.data.DP.RepositoryImplBD;


public class PresenterListOfGameFragment implements IPresenterOfNonCompleteGame {
    private RepositoryImplBD repository;
    private IListOfNotCompletedGameFragment view;

    public PresenterListOfGameFragment(Activity activity){
        repository = new RepositoryImplBD(activity);
    }

    @Override
    public void onResume() {
        view.refreshListOfCompleteGame(repository.getListGames());
    }

    @Override
    public void setView(IListOfNotCompletedGameFragment view) {
        this.view = view;
    }
}
