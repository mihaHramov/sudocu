package com.example.miha.sudocu;

import android.app.Application;

import com.example.miha.sudocu.data.DP.Login;
import com.example.miha.sudocu.data.DP.RetroClient;
import com.example.miha.sudocu.data.DP.intf.IRepository;
import com.example.miha.sudocu.data.DP.intf.IRepositoryUser;
import com.example.miha.sudocu.data.DP.RepositoryImplBD;
import com.example.miha.sudocu.data.DP.RepositorySettings;
import com.example.miha.sudocu.data.DP.RepositoryUser;
import com.example.miha.sudocu.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.presenter.PresenterGrid;
import com.example.miha.sudocu.presenter.PresenterListOfCompleteGameFragment;
import com.example.miha.sudocu.presenter.PresenterListOfGameFragment;
import com.example.miha.sudocu.presenter.PresenterMainActivity;
import com.example.miha.sudocu.presenter.PresenterRegistrationFragment;
import com.example.miha.sudocu.presenter.PresenterSettings;

import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.schedulers.Schedulers;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Scheduler  my = Schedulers.from(Executors.newSingleThreadExecutor());
        IRepository repository = new RepositoryImplBD(this);
        IRepositoryUser repositoryUser = new RepositoryUser(this);
        IRepositorySettings repositorySettings = new RepositorySettings(getApplicationContext());
        PresenterListOfGameFragment presenterListOfGameFragment = new PresenterListOfGameFragment(repository);
        presenterListOfGameFragment.setScheduler(my);
        PresenterGrid  presenterGrid = new PresenterGrid(repository,repositorySettings);
        presenterGrid.setScheduler(my);
        DP.setDp(new DP());
        DP.get().setPresenterRegistrationFragment(new PresenterRegistrationFragment(new Login(RetroClient.getInstance()),repositoryUser))
                .setPresenterListOfCompleteGameFragment(new PresenterListOfCompleteGameFragment(repository, repositoryUser))
                .setPresenterListOfGameFragment(presenterListOfGameFragment)
                .setPresenterSettings(new PresenterSettings(repositorySettings))
                .setPresenterGrid(presenterGrid)
                .setPresenterMainActivity(new PresenterMainActivity(repositorySettings));
    }
}
