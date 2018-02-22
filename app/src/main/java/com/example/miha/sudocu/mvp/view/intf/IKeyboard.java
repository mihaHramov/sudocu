package com.example.miha.sudocu.mvp.view.intf;

import com.arellomobile.mvp.MvpView;

import java.util.Map;


public interface IKeyboard extends MvpView {
    void showCountOfAnswer(Map<String,Integer> count);
    void clearCountOfAnswer();
    void postEventToClearErrorAndSameAnswerOnUI();
    void postEventToUpdateUIAfterDeleteAnswer();

    void postEventToShowNewAnswer();
}
