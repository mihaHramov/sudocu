package com.example.miha.sudocu;

import android.app.Application;

import com.example.miha.sudocu.data.DP.IRepository;
import com.example.miha.sudocu.data.DP.IRepositoryUser;
import com.example.miha.sudocu.data.DP.RepositoryImplBD;
import com.example.miha.sudocu.data.DP.RepositoryUser;
import com.example.miha.sudocu.presenter.PresenterGrid;
import com.example.miha.sudocu.presenter.PresenterListOfCompleteGameFragment;
import com.example.miha.sudocu.presenter.PresenterListOfGameFragment;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        IRepository repository = new RepositoryImplBD(this);
        IRepositoryUser repositoryUser = new RepositoryUser(this);
        DP.setDp(new DP());
        DP.get()
                .setPresenterListOfCompleteGameFragment(new PresenterListOfCompleteGameFragment(repository,repositoryUser))
                .setPresenterListOfGameFragment(new PresenterListOfGameFragment(repository))
                .setPresenterGrid(new PresenterGrid(repository));
    }
}
