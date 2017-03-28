package com.example.miha.sudocu.presenter.IPresenter;

import android.app.Activity;
import android.os.Bundle;


/**
 * Created by miha on 16.11.2016.
 */
public interface IPresenterGrid extends IPresenter {
    void init(Bundle onSaved, Activity activity);

    void answer(String answer);

    void savedPresenter();

    void loadGameTime();
}
