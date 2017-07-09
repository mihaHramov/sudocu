package com.example.miha.sudocu.presenter;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.View.IView.IListOfCompleteGameFragment;
import com.example.miha.sudocu.data.DP.ChallengeDP;
import com.example.miha.sudocu.data.DP.ChallengeDpImpl;
import com.example.miha.sudocu.data.DP.IRepositoryUser;
import com.example.miha.sudocu.data.DP.RepositoryImplBD;
import com.example.miha.sudocu.data.DP.RepositoryUser;
import com.example.miha.sudocu.data.DP.RetroClient;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.presenter.Adapter.AdapterGrid;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterOfCompleteGame;


public class PresenterListOfCompleteGameFragment implements IPresenterOfCompleteGame {
    private RepositoryImplBD repository;
    private AdapterGrid adapter;
    private ChallengeDP challengeDP = new ChallengeDpImpl(RetroClient.getInstance());
    private Activity activity;
    private IRepositoryUser repositoryUser;
    private IListOfCompleteGameFragment view;
    private ChallengeDP.ChallengeDPSendGameCallbacks sendGameCallbacks = new ChallengeDP.ChallengeDPSendGameCallbacks() {
        @Override
        public void onSuccess() {
            String toastMessage = activity.getString(R.string.send_game_to_challenge);
            Toast.makeText(activity, toastMessage, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(String message) {
            Log.d("error", message);
        }
    };

    @Override
    public void deleteGame(int id) {
        repository.deleteGame((Grid) adapter.getItem(id));
        adapter.deleteItemById(id);
    }

    @Override
    public void sendGame(int id) {
        if (repositoryUser.getUser() != null) {
            Log.d("mihaHramov","sendGame");
            challengeDP.sendGame(repositoryUser.getUser(), (Grid) adapter.getItem(id), sendGameCallbacks);
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
