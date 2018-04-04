package com.example.miha.sudocu.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.arellomobile.mvp.Pair;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.miha.sudocu.App;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.data.model.Answer;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.mvp.presenter.PresenterGrid;
import com.example.miha.sudocu.mvp.view.events.OnAfterGameChangeEvent;
import com.example.miha.sudocu.mvp.view.events.OnGameOverEvent;
import com.example.miha.sudocu.mvp.view.intf.IGetGame;
import com.example.miha.sudocu.mvp.view.intf.IGridView;
import com.squareup.otto.Subscribe;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class PlayingFieldFragment extends BaseMvpFragment implements IGridView {
    private TextView[] textViewsGrid;
    private Map<Integer, Integer> textBackgroundResource = new Hashtable<>();
    private Map<Integer, TableLayout> tableLayouts = new Hashtable<>();
    private int arrIntIdGrid[] = new int[]{R.id.top_left, R.id.top_center, R.id.top_right, R.id.middle_left, R.id.middle_center, R.id.middle_right, R.id.bottom_left, R.id.bottom_center, R.id.bottom_right};
    private Map<FrameLayout, String> map = new HashMap<>();

    @BindView(R.id.history_forward)
    Button historyForwardButton;
    @BindView(R.id.history_back)
    Button historyBackButton;
    @BindView(R.id.delete_answer)
    Button deleteButton;


    @InjectPresenter
    PresenterGrid presenterGrid;

    @ProvidePresenter
    PresenterGrid providePresenter() {
        IPresenterGrid presenterGrid = App.getComponent().playingFragment().getPresenter();
        if (getActivity() instanceof IGetGame) {
            presenterGrid.setModel(((IGetGame) getActivity()).getGame());
        }
        return (PresenterGrid) presenterGrid;
    }

    @Override
    public int getLayoutId() {
        return R.layout.playing_fragment;
    }

    @Override
    public void showCountOfAnswer(Map<String, String> count) {
        for (Map.Entry<FrameLayout, String> entry : map.entrySet()) {
            TextView subTextView = (TextView) entry.getKey().getChildAt(1);
            String sCount = count.get(entry.getValue());
            subTextView.setText(sCount);
            ((TextView) entry.getKey().getChildAt(0)).setText(entry.getValue());
        }
    }

    @Override
    public void gameOver() {
        for (Map.Entry<FrameLayout, String> val : map.entrySet()) {
            val.getKey().setEnabled(false);
        }
        deleteButton.setEnabled(false);
        historyBackButton.setEnabled(false);
        historyForwardButton.setEnabled(false);
        bus.post(new OnGameOverEvent());
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9})
    void clickOnButton(FrameLayout v) {
        presenterGrid.answer(map.get(v));
    }

    private void initMap(View v) {
        map.put((FrameLayout) v.findViewById(R.id.button1), "1");
        map.put((FrameLayout) v.findViewById(R.id.button2), "2");
        map.put((FrameLayout) v.findViewById(R.id.button3), "3");
        map.put((FrameLayout) v.findViewById(R.id.button4), "4");
        map.put((FrameLayout) v.findViewById(R.id.button5), "5");
        map.put((FrameLayout) v.findViewById(R.id.button6), "6");
        map.put((FrameLayout) v.findViewById(R.id.button7), "7");
        map.put((FrameLayout) v.findViewById(R.id.button8), "8");
        map.put((FrameLayout) v.findViewById(R.id.button9), "9");
        deleteButton.setOnClickListener(view -> presenterGrid.deleteAnswer());
        historyForwardButton.setOnClickListener(view -> presenterGrid.historyForward());
        historyBackButton.setOnClickListener(view -> presenterGrid.historyBack());
    }

    @Override
    public void disableButtonHistoryButton(Boolean back, Boolean forward) {
        historyForwardButton.setEnabled(forward);
        historyBackButton.setEnabled(back);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        for (int anArrIntIdGrid : arrIntIdGrid) {
            tableLayouts.put(anArrIntIdGrid, (TableLayout) rootView.findViewById(anArrIntIdGrid));
        }
        initMap(rootView);
        App.getComponent().playingFragment().inject(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenterGrid.onResume();
    }

    @Override
    public void clearField(List<Integer> theSameAnswers) {
        for (Integer id : theSameAnswers) {
            textViewsGrid[id].setBackgroundResource(textBackgroundResource.get(id));
            textViewsGrid[id].setTextColor(getResources().getColor(R.color.black));
        }
    }


    @Override
    public void removeFocus(Integer id) {
        if (id == null) return;
        int background = textBackgroundResource.get(id);
        textViewsGrid[id].setBackgroundResource(background);
    }

    @Override
    public void setFocus(Integer id, Boolean isError) {
        if (isError) {
            textViewsGrid[id].setBackgroundResource(R.drawable.focus_error_not_know);
            textViewsGrid[id].setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            textViewsGrid[id].setBackgroundResource(R.drawable.focus);
        }
    }

    @Override
    public void colorThePlayingField(List<Pair<Integer, Integer>> pairs) {
        for (Pair<Integer, Integer> pair : pairs) {
            textViewsGrid[pair.first].setBackgroundResource(pair.second);
        }
    }

    @Override
    public void showError(List<Answer> list) {
        if (list == null) return;
        for (Answer answer : list) {
            Integer resDrawable = answer.isAnswer() ? R.drawable.show_error_answer : R.drawable.show_error_field;
            textViewsGrid[answer.getId()].setBackgroundResource(resDrawable);
            textViewsGrid[answer.getId()].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public void showGrid(Answer[][] grid) {
        int countOfRowsAndCols = grid.length;
        textViewsGrid = new TextView[countOfRowsAndCols * countOfRowsAndCols];
        for (int i = 0; i < countOfRowsAndCols; i++) {
            for (int j = 0; j < countOfRowsAndCols; j++) {
                int resI = (i / 3) * 3;
                int resJ = j / 3;
                TableLayout tab = tableLayouts.get(arrIntIdGrid[resI + resJ]);//выбрал необходимый квадрат
                TableRow row = (TableRow) tab.getChildAt(i % 3);
                TextView textView = (TextView) row.getChildAt(j % 3);
                textView.setText(grid[i][j].getNumber());
                textView.setId(i * countOfRowsAndCols + j);
                textView.setTextColor(getResources().getColor(R.color.black));
                textViewsGrid[i * countOfRowsAndCols + j] = textView;
                textView.setOnClickListener(v -> presenterGrid.choseInput(v.getId()));
                int backgroundResource = grid[i][j].isAnswer() ? R.drawable.background_grey : R.drawable.back;
                textView.setBackgroundResource(backgroundResource);
                textBackgroundResource.put(i * countOfRowsAndCols + j, backgroundResource);
            }
        }
    }

    @Override
    public void setTextToAnswer(Answer answer) {
        textViewsGrid[answer.getId()].setText(answer.getNumber());
    }

    @Subscribe
    public void replayGame(OnAfterGameChangeEvent event) {
        presenterGrid.onResume();
    }
}