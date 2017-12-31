package com.example.miha.sudocu.data.DP;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.miha.sudocu.data.DP.intf.IRepositorySettings;

public class RepositorySettings implements IRepositorySettings {
    private static final String APP_SHARED_PREFERENCE = "APP_SHARED_PREFERENCE";
    private static final String KeyBoardMode = "KeyBoardMode";
    private static final String ShowErrorMode = "ShowErrorMode";
    private static final String ShowSameAnswerMode = "ShowSameAnswerMode";
    private static final String ShowKnowAnswerMode = "ShowKnowAnswerMode";
    private static final String ShowCountNumberOnButtonMode = "ShowCountNumberOnButtonMode";

    private SharedPreferences repository;

    public RepositorySettings(Context context) {
        this.repository = context.getSharedPreferences(APP_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }

    @Override
    public void changeKeyboardMode(Boolean flag) {
        repository.edit().putBoolean(KeyBoardMode, flag).apply();
    }

    @Override
    public void changeShowErrorMode(boolean isChecked) {
        repository.edit().putBoolean(ShowErrorMode, isChecked).apply();
    }

    @Override
    public void changeShowKnowAnswerMode(boolean isChecked) {
        repository.edit().putBoolean(ShowKnowAnswerMode, isChecked).apply();
    }

    @Override
    public void changeShowCountNumberOnButtonMode(boolean isChecked) {
        repository.edit().putBoolean(ShowCountNumberOnButtonMode, isChecked).apply();
    }

    @Override
    public void changeShowSameAnswerMode(boolean isChecked) {
        repository.edit().putBoolean(ShowSameAnswerMode, isChecked).apply();
    }

    @Override
    public Boolean getKeyboardMode() {
        return repository.getBoolean(KeyBoardMode, false);
    }

    @Override
    public Boolean getErrorMode() {
        return repository.getBoolean(ShowErrorMode, true);
    }

    @Override
    public Boolean getKnowAnswerMode() {
        return repository.getBoolean(ShowKnowAnswerMode, true);
    }

    @Override
    public Boolean getShowCountNumberOnButtonMode() {
        return repository.getBoolean(ShowCountNumberOnButtonMode, true);
    }

    @Override
    public Boolean getShowSameAnswerMode() {
        return repository.getBoolean(ShowSameAnswerMode, true);
    }
}
