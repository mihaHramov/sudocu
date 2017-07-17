package com.example.miha.sudocu.presenter;

import android.app.Activity;
import android.widget.ListView;

import com.example.miha.sudocu.View.IView.IListOfCompleteGameFragment;
import com.example.miha.sudocu.data.DP.ChallengeDP;
import com.example.miha.sudocu.data.DP.ChallengeDpImpl;
import com.example.miha.sudocu.data.DP.IRepositoryUser;
import com.example.miha.sudocu.data.DP.RepositoryImplBD;
import com.example.miha.sudocu.data.DP.RepositoryUser;
import com.example.miha.sudocu.data.DP.RetroClient;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.model.User;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfCompleteGame;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PresenterListOfCompleteGameFragment implements IPresenterOfCompleteGame {
    private RepositoryImplBD repository;
    private ChallengeDP challengeDP = new ChallengeDpImpl(RetroClient.getInstance());
    private IRepositoryUser repositoryUser;
    private IListOfCompleteGameFragment view;

    @Override
    public void deleteGame(Grid grid) {
        repository.deleteGame(grid);
        view.refreshListOfCompleteGame(repository.getListCompleteGames());
    }

    @Override
    public void sendGame(Grid grid) {
        User user = repositoryUser.getUser();
        if ( user != null) {
            challengeDP.sendGame(user,grid)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aVoid -> view.onSendGame(), throwable -> view.onErrorSendGame());
        } else {
            view.authUser();
        }
    }

    @Override
    public void setView(IListOfCompleteGameFragment view) {
        this.view = view;
    }

    public PresenterListOfCompleteGameFragment(Activity activity) {
        repository = new RepositoryImplBD(activity);
        repositoryUser = new RepositoryUser(activity);
    }



    @Override
    public void onResume() {
        view.refreshListOfCompleteGame(repository.getListCompleteGames());
    }
}
