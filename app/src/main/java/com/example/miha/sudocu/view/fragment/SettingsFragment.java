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

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingsFragment extends Fragment implements ISettingsFragment {
    private PresenterSettings presenterSettings;

    @BindView(R.id.switch_keyboard_mode)
    Switch switchKeyboard;
    @BindView(R.id.show_error_answer)
    Switch switchShowError;
    @BindView(R.id.show_count_number_on_button)
    Switch switchShowCountNumberOnButton;
    @BindView(R.id.show_know_answer)
    Switch switchShowKnowAnswer;
    @BindView(R.id.show_same_answer)
    Switch switchShowSameAnswer;

    @Override
    public void setKeyboardMod(Boolean flag) {
        switchKeyboard.setChecked(flag);
    }

    @Override
    public void setShowSameAnswerMode(Boolean showSameAnswerMode) {
        switchShowSameAnswer.setChecked(showSameAnswerMode);
    }

    @Override
    public void setShowCountNumberOnButtonMode(Boolean showCountNumberOnButtonMode) {
        switchShowCountNumberOnButton.setChecked(showCountNumberOnButtonMode);
    }

    @Override
    public void setShowKnowAnswerMode(Boolean knowAnswerMode) {
        switchShowKnowAnswer.setChecked(knowAnswerMode);
    }

    @Override
    public void setShowErrorMode(Boolean errorMode) {
        switchShowError.setChecked(errorMode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenterSettings = DP.get().getPresenterSettings();
        View v = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, v);
        switchKeyboard.setOnCheckedChangeListener((buttonView, isChecked) -> presenterSettings.changeKeyboardMode(isChecked));
        switchShowError.setOnCheckedChangeListener((buttonView, isChecked) -> presenterSettings.changeShowErrorMode(isChecked));
        switchShowKnowAnswer.setOnCheckedChangeListener((buttonView, isChecked) -> presenterSettings.changeShowKnowAnswerMode(isChecked));
        switchShowSameAnswer.setOnCheckedChangeListener((buttonView, isChecked) -> presenterSettings.changeShowSameAnswerMode(isChecked));
        switchShowCountNumberOnButton.setOnCheckedChangeListener((buttonView, isChecked) -> presenterSettings.changeShowCountNumberOnButtonMode(isChecked));
        presenterSettings.init(this);
        return v;
    }
}
