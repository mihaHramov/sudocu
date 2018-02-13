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
import com.example.miha.sudocu.presenter.PresenterListOfCompleteGameFragment;
import com.example.miha.sudocu.presenter.PresenterListOfGameFragment;
import com.example.miha.sudocu.presenter.PresenterMainActivity;
import com.example.miha.sudocu.presenter.PresenterRegistrationFragment;
import com.example.miha.sudocu.presenter.PresenterSettings;

import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.schedulers.Schedulers;


public class App extends Application {
    private static  Scheduler my;
    public static Scheduler getDBsheduler(){
        return my;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        my = Schedulers.from(Executors.newSingleThreadExecutor());
        IRepository repository = new RepositoryImplBD(this);
        IRepositoryUser repositoryUser = new RepositoryUser(this);
        IRepositorySettings repositorySettings = new RepositorySettings(getApplicationContext());
        DP.setDp(new DP());
        PresenterListOfGameFragment presenterListOfGameFragment = new PresenterListOfGameFragment(repository);
        presenterListOfGameFragment.setScheduler(my);
        DP.get().setPresenterRegistrationFragment(new PresenterRegistrationFragment(new Login(RetroClient.getInstance()),repositoryUser))
                .setPresenterListOfCompleteGameFragment(new PresenterListOfCompleteGameFragment(repository, repositoryUser))
                .setPresenterListOfGameFragment(presenterListOfGameFragment)
                .setPresenterSettings(new PresenterSettings(repositorySettings))
                .setPresenterMainActivity(new PresenterMainActivity(repositorySettings));
    }
}
