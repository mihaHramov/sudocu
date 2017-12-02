package com.example.miha.sudocu.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.example.miha.sudocu.DP;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.presenter.PresenterSettings;
import com.example.miha.sudocu.view.IView.ISettingsFragment;


public class SettingsFragment extends Fragment implements ISettingsFragment {
    private PresenterSettings presenterSettings;

    private Switch switchKeyboard;

    @Override
    public void setKeyboardMod(Boolean flag) {
        switchKeyboard.setChecked(flag);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenterSettings = DP.get().getPresenterSettings();
        View v = inflater.inflate(R.layout.settings_fragment, container, false);
        switchKeyboard = (Switch) v.findViewById(R.id.switch_keyboard_mode);
        switchKeyboard.setOnCheckedChangeListener((buttonView, isChecked) -> presenterSettings.changeKeyboardMode(isChecked));
        presenterSettings.init(this);
        return v;
    }
}
