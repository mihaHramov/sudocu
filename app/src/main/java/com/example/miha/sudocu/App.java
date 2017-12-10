package com.example.miha.sudocu;

import android.app.Application;

import com.example.miha.sudocu.data.DP.IRepository;
import com.example.miha.sudocu.data.DP.IRepositoryUser;
import com.example.miha.sudocu.data.DP.RepositoryImplBD;
import com.example.miha.sudocu.data.DP.RepositorySettings;
import com.example.miha.sudocu.data.DP.RepositoryUser;
import com.example.miha.sudocu.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.presenter.PresenterGrid;
import com.example.miha.sudocu.presenter.PresenterListOfCompleteGameFragment;
import com.example.miha.sudocu.presenter.PresenterListOfGameFragment;
import com.example.miha.sudocu.presenter.PresenterMainActivity;
import com.example.miha.sudocu.presenter.PresenterSettings;

import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.schedulers.Schedulers;


public class App extends Application {
    private static final String APP_SHARED_PREFERENCE = "APP_SHARED_PREFERENCE";

    @Override
    public void onCreate() {
        super.onCreate();
        Scheduler my = Schedulers.from(Executors.newSingleThreadExecutor());
        IRepository repository = new RepositoryImplBD(this);
        IRepositoryUser repositoryUser = new RepositoryUser(this);
        IRepositorySettings repositorySettings = new RepositorySettings(getSharedPreferences(APP_SHARED_PREFERENCE, MODE_PRIVATE));
        DP.setDp(new DP());
        PresenterListOfGameFragment presenterListOfGameFragment = new PresenterListOfGameFragment(repository);
        PresenterGrid presenterGrid = new PresenterGrid(repository, repositorySettings);
        presenterGrid.setScheduler(my);
        presenterListOfGameFragment.setScheduler(my);
        DP.get()
                .setPresenterListOfCompleteGameFragment(new PresenterListOfCompleteGameFragment(repository, repositoryUser))
                .setPresenterListOfGameFragment(presenterListOfGameFragment)
                .setPresenterGrid(presenterGrid)
                .setPresenterSettings(new PresenterSettings(repositorySettings))
                .setPresenterMainActivity(new PresenterMainActivity(repositorySettings));
    }
}
