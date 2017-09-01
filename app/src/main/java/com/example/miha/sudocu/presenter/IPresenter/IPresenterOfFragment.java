package com.example.miha.sudocu.presenter.IPresenter;


import android.os.Bundle;

public interface IPresenterOfFragment {
    void onResume();
    void init(Bundle bundle);
    void savePresenterData(Bundle bundle);
}
