package com.example.miha.sudocu.mvp.data.DP.intf;


public interface IRepositorySettings {
    Boolean getKeyboardMode();

    Boolean getErrorMode();

    Boolean getKnowAnswerMode();

    Boolean getShowCountNumberOnButtonMode();

    Boolean getShowSameAnswerMode();

    void changeKeyboardMode(Boolean flag);

    void changeShowErrorMode(boolean isChecked);

    void changeShowKnowAnswerMode(boolean isChecked);

    void changeShowCountNumberOnButtonMode(boolean isChecked);

    void changeShowSameAnswerMode(boolean isChecked);
}
