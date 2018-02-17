package com.example.miha.sudocu.mvp.presenter;


import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.view.intf.ISettingsFragment;

import javax.inject.Inject;

public class PresenterSettings {
    private IRepositorySettings settings;

    @Inject
    public PresenterSettings(IRepositorySettings repositorySettings) {
        settings = repositorySettings;
    }

    public void changeKeyboardMode(Boolean flag) {
        settings.changeKeyboardMode(flag);
    }

    public void init(ISettingsFragment fr) {
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
