package com.example.miha.sudocu.presenter;

import android.content.Context;
import android.os.Bundle;

import com.example.miha.sudocu.View.IView.ISettings;
import com.example.miha.sudocu.data.IData.ISettingComplexity;
import com.example.miha.sudocu.data.SettingComplexity;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterSettings;

/**
 * Created by miha on 17.11.2016.
 */
public class PresenterSettings implements IPresenterSettings {

    private ISettings view;
    private ISettingComplexity model;
    public  PresenterSettings(ISettings settings){
        view = settings;
        model = new SettingComplexity((Context)view);
    }
    @Override
    public void save(String s) {
        model.save(Integer.parseInt(s));
        view.success();
    }

}
