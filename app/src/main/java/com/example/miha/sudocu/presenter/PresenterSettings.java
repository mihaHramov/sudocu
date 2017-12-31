package com.example.miha.sudocu.presenter;


import com.example.miha.sudocu.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.view.intf.ISettingsFragment;

public class PresenterSettings {
    private ISettingsFragment view;
    private IRepositorySettings settings;

    public PresenterSettings(IRepositorySettings repositorySettings) {
        settings = repositorySettings;
    }

    public void changeKeyboardMode(Boolean flag) {
        settings.changeKeyboardMode(flag);
    }

    public void init(ISettingsFragment fr) {
        view = fr;
        fr.setKeyboardMod(settings.getKeyboardMode());
        fr.setShowSameAnswerMode(settings.getShowSameAnswerMode());
        fr.setShowCountNumberOnButtonMode(settings.getShowCountNumberOnButtonMode());
        fr.setShowKnowAnswerMode(settings.getKnowAnswerMode());
        fr.setShowErrorMode(settings.getErrorMode());
    }

    public void changeShowSameAnswerMode(boolean isChecked) {
        settings.changeShowSameAnswerMode(isChecked);
    }

    public void changeShowCountNumberOnButtonMode(boolean isChecked) {
        settings.changeShowCountNumberOnButtonMode(isChecked);
    }

    public void changeShowKnowAnswerMode(boolean isChecked) {
        settings.changeShowKnowAnswerMode(isChecked);
    }

    public void changeShowErrorMode(boolean isChecked) {
        settings.changeShowErrorMode(isChecked);
    }
}
