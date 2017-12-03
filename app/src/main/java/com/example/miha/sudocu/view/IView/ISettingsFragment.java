package com.example.miha.sudocu.view.IView;


public interface ISettingsFragment {
    void setKeyboardMod(Boolean flag);

    void setShowSameAnswerMode(Boolean showSameAnswerMode);

    void setShowCountNumberOnButtonMode(Boolean showCountNumberOnButtonMode);

    void setShowKnowAnswerMode(Boolean knowAnswerMode);

    void setShowErrorMode(Boolean errorMode);
}
