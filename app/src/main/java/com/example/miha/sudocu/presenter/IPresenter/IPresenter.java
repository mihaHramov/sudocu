package com.example.miha.sudocu.presenter.IPresenter;

import android.os.Bundle;

/**
 * Created by miha on 16.11.2016.
 */
public interface IPresenter {
    public void onSaveInstanceState(Bundle outState);
    public  void init(Bundle onSaved);
    public void unSubscription();
}
