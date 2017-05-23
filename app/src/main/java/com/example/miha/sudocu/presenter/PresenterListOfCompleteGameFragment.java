package com.example.miha.sudocu.presenter;

import android.app.Activity;
import android.widget.ListView;

import com.example.miha.sudocu.data.DP.ChallengeDP;
import com.example.miha.sudocu.data.DP.ChallengeDpImpl;
import com.example.miha.sudocu.data.DP.IRepositoryUser;
import com.example.miha.sudocu.data.DP.RepositoryImplBD;
import com.example.miha.sudocu.data.DP.RepositoryUser;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.presenter.Adapter.AdapterGrid;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfCompleteGame;



public class PresenterListOfCompleteGameFragment implements IPresenterOfCompleteGame {
    private RepositoryImplBD repository;
    private AdapterGrid adapter;
    private ChallengeDP challengeDP = new ChallengeDpImpl();
    private Activity activity;
    private IRepositoryUser repositoryUser;

    private ChallengeDP.ChallengeDPSendGameCallbacks sendGameCallbacks = new ChallengeDP.ChallengeDPSendGameCallbacks() {
        @Override
        public void onSuccess(Object challenges) {

        }

        @Override
        public void onError() {

        }
    };

    @Override
    public void deleteGame(int id) {
        repository.deleteGame((Grid) adapter.getItem(id));
        adapter.deleteItemById(id);

    }

    @Override
    public void sendGame(int id) {
        challengeDP.sendGame(repositoryUser.getUser(),(Grid) adapter.getItem(id), sendGameCallbacks);
    }

    public PresenterListOfCompleteGameFragment(Activity activity){
        repository = new RepositoryImplBD(activity);
        repositoryUser = new RepositoryUser(activity);
        adapter = new AdapterGrid(activity);
        this.activity = activity;
    }

    @Override
    public void initListView(ListView listView) {
        listView.setAdapter(adapter);

    }


    @Override
    public void onResume() {
        adapter.setData(repository.getListCompleteGames());
    }
}
