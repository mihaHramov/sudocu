package com.example.miha.sudocu.presenter;


import com.example.miha.sudocu.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.view.IView.ISettingsFragment;

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
    }
}
