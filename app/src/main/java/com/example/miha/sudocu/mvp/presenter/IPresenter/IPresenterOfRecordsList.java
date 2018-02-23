package com.example.miha.sudocu.mvp.presenter.IPresenter;


import com.example.miha.sudocu.mvp.data.model.Challenge;

public interface IPresenterOfRecordsList {
    void onResume();
    void choiceChallenge(Challenge challenge);
}
