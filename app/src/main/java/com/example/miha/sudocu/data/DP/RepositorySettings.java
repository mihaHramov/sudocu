package com.example.miha.sudocu.data.DP;


import android.content.SharedPreferences;

import com.example.miha.sudocu.data.DP.intf.IRepositorySettings;

public class RepositorySettings implements IRepositorySettings{
    private static final String KeyBoardMode= "KeyBoardMode";
    private SharedPreferences repository;

    public RepositorySettings(SharedPreferences repository) {
        this.repository = repository;
    }

    @Override
    public void changeKeyboardMode(Boolean flag){
        repository.edit().putBoolean(KeyBoardMode,flag).apply();
    }

    @Override
    public Boolean getKeyboardMode() {
        return repository.getBoolean(KeyBoardMode,false);
    }
}
