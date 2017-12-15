package com.example.miha.sudocu.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miha.sudocu.DP;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.model.Answer;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.utils.ConverterTime;
import com.example.miha.sudocu.view.IView.IGridView;
import com.example.miha.sudocu.view.IView.IMainActivity;
import com.example.miha.sudocu.view.events.BusProvider;
import com.example.miha.sudocu.view.events.OnChangeCountOfAnswer;
import com.example.miha.sudocu.view.events.OnChangeHistoryGame;
import com.example.miha.sudocu.view.events.OnChangeShowCountAnswerMode;
import com.example.miha.sudocu.view.events.PlayMusicEvent;
import com.example.miha.sudocu.view.events.OnAnswerChangeEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class PlayingFieldFragment extends Fragment implements IGridView {
    private Bus bus;
    private IPresenterGrid presenterGrid;
    private TextView[] textViewsGrid;
    private Map<Integer, Integer> textBackgroundResource = new Hashtable<>();
    private Map<Integer, TableLayout> tableLayouts = new Hashtable<>();
    private int arrIntIdGrid[] = new int[]{R.id.top_left, R.id.top_center, R.id.top_right, R.id.middle_left, R.id.middle_center, R.id.middle_right, R.id.bottom_left, R.id.bottom_center, R.id.bottom_right};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenterGrid = DP.get().getPresenterOfGrid();
        bus = BusProvider.getInstance();
        bus.register(this);
        View rootView = inflater.inflate(R.layout.playing_fragment, container, false);
        for (int anArrIntIdGrid : arrIntIdGrid) {
            tableLayouts.put(anArrIntIdGrid, (TableLayout) rootView.findViewById(anArrIntIdGrid));
        }
        return rootView;
    }

    @Override
    public void clearCountOfAnswer() {
        bus.post(new OnChangeShowCountAnswerMode());
    }

    @Override
    public void showCountOfAnswer(Map<String, Integer> count) {
        bus.post(new OnChangeCountOfAnswer(count));
    }

    @Override
    public void onResume() {
        presenterGrid.init(getActivity().getIntent());
        presenterGrid.setView(this);
        presenterGrid.onResume();
        super.onResume();
    }

    @Override
    public void setGameName(String name) {
        ((IMainActivity) getActivity()).changeTitleToolbar(name);
    }

    @Override
    public void onPause() {
        presenterGrid.onPause();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        presenterGrid.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Subscribe
    public void clickOnButton(OnAnswerChangeEvent answer) {
        presenterGrid.answer(answer.getAnswer());
    }

    @Subscribe
    public void changeHistory(OnChangeHistoryGame change) {
        if (change.getForward()) {
            presenterGrid.historyForward();
        } else {
            presenterGrid.historyBack();
        }
    }

    @Override
    public void onDestroy() {
        bus.unregister(this);
        presenterGrid.unSubscription();
        super.onDestroy();
    }

    @Override
    public void clearError(ArrayList<Integer> list) {
        if (list != null && !list.isEmpty()) {
            for (Integer i : list) {
                textViewsGrid[i].setBackgroundResource(textBackgroundResource.get(i));
                textViewsGrid[i].setTextColor(getResources().getColor(R.color.black));
            }
        }
    }

    @Override
    public void showTheSameAnswers(ArrayList<Integer> list) {
        list(list, R.drawable.show_same_answer);
    }

    @Override
    public void clearTheSameAnswer(ArrayList<Integer> theSameAnswers) {
        if (!theSameAnswers.isEmpty()) {
            for (Integer id : theSameAnswers) {
                textViewsGrid[id].setBackgroundResource(textBackgroundResource.get(id));
            }
        }
    }


    @Override
    public void removeFocus(Integer id) {
        if (id == null) return;
        int background = textBackgroundResource.get(id);
        textViewsGrid[id].setBackgroundResource(background);
    }

    @Override
    public void showErrorFocus(int id) {
        textViewsGrid[id].setBackgroundResource(R.drawable.focus_error);
        textViewsGrid[id].setTextColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void setFocus(Integer id) {
        textViewsGrid[id].setBackgroundResource(R.drawable.focus);
    }

    @Override
    public void clearKnownOptions(ArrayList<Integer> list) {
        for (Integer iterator : list) {
            textViewsGrid[iterator].setBackgroundResource(textBackgroundResource.get(iterator));
        }
    }

    @Override
    public void showKnownOptions(ArrayList<Integer> list) {
        list(list, R.drawable.show_all_know_answer);
    }

    @Override
    public void showError(ArrayList<Integer> list) {
        if (list != null && !list.isEmpty()) {
            for (Integer i : list) {
                textViewsGrid[i].setBackgroundResource(R.drawable.show_error_answer);
                textViewsGrid[i].setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }
    }

    public void list(ArrayList<Integer> list, int res) {
        if (!list.isEmpty()) {
            for (Integer id : list) {
                textViewsGrid[id].setBackgroundResource(res);
            }
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
                textViewsGrid[i * countOfRowsAndCols + j] = textView;
                textView.setOnClickListener(v -> presenterGrid.choseInput(v.getId()));
                int backgroundResource = grid[i][j].isAnswer() ? R.drawable.background_grey : R.drawable.back;
                textView.setBackgroundResource(backgroundResource);
                textBackgroundResource.put(i * countOfRowsAndCols + j, backgroundResource);
            }
        }
    }

    @Override
    public void setTextToAnswer(Integer id, String answer) {
        textViewsGrid[id].setText(answer);
    }

    @Override
    public void setGameTime(long time) {
        ConverterTime converterTime = ConverterTime.getInstance();
        Long minutes = converterTime.converterLongToMinutes(time);
        Long second = converterTime.converterLongToSeconds(time);
        ((IMainActivity) getActivity()).changeSubTitleToolbar(Long.toString(minutes) + ":" + Long.toString(second));
    }

    @Override
    public void gameOver() {
        Toast.makeText(getActivity(), "game over", Toast.LENGTH_SHORT).show();
        bus.post(new PlayMusicEvent(R.raw.success));
    }

    public void reloadGame() {
        presenterGrid.reloadGame();
    }

    public void replayGame() {
        presenterGrid.replayGame();
    }
}