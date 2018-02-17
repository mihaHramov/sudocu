package com.example.miha.sudocu.mvp.presenter.IPresenter;


import com.example.miha.sudocu.mvp.data.model.Challenge;
import com.example.miha.sudocu.mvp.view.intf.IRecordsList;

public interface IPresenterOfRecordsList extends IPresenterOfFragment {
    void choiceChallenge(Challenge challenge);
    void setView(IRecordsList recordsList);
}
