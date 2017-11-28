package com.example.miha.sudocu.view.fragment;

import android.content.Context;
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

import butterknife.ButterKnife;
import butterknife.OnClick;


public class KeyBoardFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9})
    void clickOnButton(View v) {
        String answer = ((Button) v).getText().toString();
        BusProvider.getInstance().post(new OnAnswerChangeEvent(answer));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BusProvider.getInstance().register(this);
        View rootView = inflater.inflate(R.layout.keyboard_fragment, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }
}
