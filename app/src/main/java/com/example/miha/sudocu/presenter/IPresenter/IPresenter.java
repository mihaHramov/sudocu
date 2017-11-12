package com.example.miha.sudocu.presenter.IPresenter;

import android.os.Bundle;

public interface IPresenter {
    void onSaveInstanceState(Bundle save);

    void unSubscription();
}
