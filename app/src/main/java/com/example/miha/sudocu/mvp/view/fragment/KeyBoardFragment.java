package com.example.miha.sudocu.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.miha.sudocu.App;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.presenter.PresenterKeyboard;
import com.example.miha.sudocu.mvp.view.events.OnAfterAnswerDeleteEvent;
import com.example.miha.sudocu.mvp.view.events.OnAfterGameChangeEvent;
import com.example.miha.sudocu.mvp.view.events.OnAnswerChangeEvent;
import com.example.miha.sudocu.mvp.view.events.OnAnswerDeleteEvent;
import com.example.miha.sudocu.mvp.view.intf.IGetGame;
import com.example.miha.sudocu.mvp.view.intf.IKeyboard;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class KeyBoardFragment extends BaseMvpFragment implements IKeyboard {
    private Map<Button, String> map = new HashMap<>();
    @BindView(R.id.history_forward)
    Button historyForwardButton;
    @BindView(R.id.history_back)
    Button historyBackButton;
    @InjectPresenter
    PresenterKeyboard presenter;

    @ProvidePresenter
    PresenterKeyboard providePresenter() {
        presenter = App.getComponent().playingFragment().getPresenterKeyboard();
        if (getActivity() instanceof IGetGame) {
            presenter.setModel(((IGetGame) getActivity()).getGame());
        }
        return presenter;
    }

    @Override
    public void showCountOfAnswer(Map<String, Integer> count) {
        for (Map.Entry<Button, String> entry : map.entrySet()) {
            Integer i = count.get(entry.getValue());
            String string = entry.getValue() + "(" + i + ")";
            entry.getKey().setText(string);
        }
    }

    @Override
    public void postEventToClearErrorAndSameAnswerOnUI() {
        bus.post(new OnAnswerDeleteEvent());
    }

    @Override
    public void postEventToShowNewAnswer() {
        bus.post(new OnAnswerChangeEvent());
    }

    @Override
    public void postEventToUpdateUIAfterDeleteAnswer() {
        bus.post(new OnAfterAnswerDeleteEvent());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void clearCountOfAnswer() {
        for (Map.Entry<Button, String> entry : map.entrySet()) {
            entry.getKey().setText(entry.getValue());
        }
    }

    @OnClick(R.id.delete_answer)
    void clickOnButtonDeleteAnswer() {
        presenter.deleteAnswer();
    }

    @OnClick(R.id.history_back)
    void clickOnButtonHistory() {
        presenter.historyBack();
    }

    @OnClick(R.id.history_forward)
    void clickOnButtonHistoryForward() {
        presenter.historyForward();
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9})
    void clickOnButton(View v) {
        presenter.answer(map.get(v));
    }

    @Subscribe
    public void replayGame(OnAfterGameChangeEvent event) {
        presenter.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        initMap(v);
        App.getComponent().playingFragment().inject(this);
        return v;
    }

    private void initMap(View v) {
        map.put((Button) v.findViewById(R.id.button1), "1");
        map.put((Button) v.findViewById(R.id.button2), "2");
        map.put((Button) v.findViewById(R.id.button3), "3");
        map.put((Button) v.findViewById(R.id.button4), "4");
        map.put((Button) v.findViewById(R.id.button5), "5");
        map.put((Button) v.findViewById(R.id.button6), "6");
        map.put((Button) v.findViewById(R.id.button7), "7");
        map.put((Button) v.findViewById(R.id.button8), "8");
        map.put((Button) v.findViewById(R.id.button9), "9");
    }

    @Override
    public void disableButtonHistoryForward(Boolean enabled) {
        historyForwardButton.setEnabled(enabled);
    }

    @Override
    public void disableButtonHistoryBack(Boolean enabled) {
        historyBackButton.setEnabled(enabled);
    }

    @Override
    public int getLayoutId() {
        return R.layout.keyboard_fragment;
    }
}
