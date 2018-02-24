package com.example.miha.sudocu.mvp.presenter.IPresenter;


import com.example.miha.sudocu.mvp.data.model.LocalChallenge;

public interface IPresenterOfRecordsList {
    void onResume();
    void choiceChallenge(LocalChallenge challenge);
}
