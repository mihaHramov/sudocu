package com.example.miha.sudocu.presenter.IPresenter;

import android.os.Bundle;

/**
 * Created by miha on 16.11.2016.
 */
public interface IPresenter {
    void onSaveInstanceState(Bundle outState);

    void unSubscription();
}
