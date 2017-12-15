package com.example.miha.sudocu.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.view.events.BusProvider;
import com.example.miha.sudocu.view.events.OnAnswerChangeEvent;
import com.example.miha.sudocu.view.events.OnChangeCountOfAnswer;
import com.example.miha.sudocu.view.events.OnChangeHistoryGame;
import com.example.miha.sudocu.view.events.OnChangeShowCountAnswerMode;
import com.squareup.otto.Subscribe;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class KeyBoardFragment extends Fragment {
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.button8)
    Button button8;
    @BindView(R.id.button9)
    Button button9;

    @OnClick({R.id.history_back,R.id.history_forward})
    void clickOnButtonHistory(View view){
        if(view.getId()==R.id.history_forward){
            BusProvider.getInstance().post(new OnChangeHistoryGame(true));
        }else {
            BusProvider.getInstance().post(new OnChangeHistoryGame(false));
        }
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9})
    void clickOnButton(View v) {
        String answer = "";
        switch (v.getId()) {
            case R.id.button1:
                answer = "1";
                break;
            case R.id.button2:
                answer = "2";
                break;
            case R.id.button3:
                answer = "3";
                break;
            case R.id.button4:
                answer = "4";
                break;
            case R.id.button5:
                answer = "5";
                break;
            case R.id.button6:
                answer = "6";
                break;
            case R.id.button7:
                answer = "7";
                break;
            case R.id.button8:
                answer = "8";
                break;
            case R.id.button9:
                answer = "9";
                break;
        }
        BusProvider.getInstance().post(new OnAnswerChangeEvent(answer));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BusProvider.getInstance().register(this);
        View rootView = inflater.inflate(R.layout.keyboard_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onChangeShowCountAnswerMode(OnChangeShowCountAnswerMode changeMode) {
        button1.setText("1");
        button2.setText("2");
        button3.setText("3");
        button4.setText("4");
        button5.setText("5");
        button6.setText("6");
        button7.setText("7");
        button8.setText("8");
        button9.setText("9");
    }

    @Subscribe
    public void onChangeCountOfAnswers(OnChangeCountOfAnswer countOfAnswer) {
        Map<String, Integer> count = countOfAnswer.getCountOfAnswers();
        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            String str = entry.getKey() + "(" + entry.getValue() + ")";
            switch (entry.getKey()) {
                case "9":
                    button9.setText(str);
                    break;
                case "8":
                    button8.setText(str);
                    break;
                case "7":
                    button7.setText(str);
                    break;
                case "6":
                    button6.setText(str);
                    break;
                case "5":
                    button5.setText(str);
                    break;
                case "4":
                    button4.setText(str);
                    break;
                case "3":
                    button3.setText(str);
                    break;
                case "2":
                    button2.setText(str);
                    break;
                case "1":
                    button1.setText(str);
                    break;
            }
        }
    }
}
