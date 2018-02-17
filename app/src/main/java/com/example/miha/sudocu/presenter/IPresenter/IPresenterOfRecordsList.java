package com.example.miha.sudocu.presenter.IPresenter;


import com.example.miha.sudocu.data.model.Challenge;
import com.example.miha.sudocu.view.intf.IRecordsList;

public interface IPresenterOfRecordsList extends IPresenterOfFragment {
    void choiceChallenge(Challenge challenge);
    void setView(IRecordsList recordsList);
}
