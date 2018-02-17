package com.example.miha.sudocu.mvp.presenter.IPresenter;

import android.os.Bundle;

public interface IPresenter {
    void onSaveInstanceState(Bundle save);

    void unSubscription();
}
