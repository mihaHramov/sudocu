package com.example.miha.sudocu;

import android.app.Application;

import com.example.miha.sudocu.presenter.PresenterListOfCompleteGameFragment;
import com.example.miha.sudocu.presenter.PresenterListOfGameFragment;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DP.setDp(new DP());
        DP.get()
                .setPresenterListOfCompleteGameFragment(new PresenterListOfCompleteGameFragment(this))
                .setPresenterListOfGameFragment(new PresenterListOfGameFragment(this));
    }
}
