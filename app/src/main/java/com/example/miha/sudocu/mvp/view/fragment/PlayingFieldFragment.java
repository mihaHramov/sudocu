package com.example.miha.sudocu.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.miha.sudocu.App;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.mvp.data.model.Answer;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.mvp.presenter.PresenterGrid;
import com.example.miha.sudocu.mvp.view.events.OnAfterAnswerDeleteEvent;
import com.example.miha.sudocu.mvp.view.events.OnAfterGameChangeEvent;
import com.example.miha.sudocu.mvp.view.intf.IGetGame;
import com.example.miha.sudocu.mvp.view.intf.IGridView;
import com.example.miha.sudocu.mvp.view.events.OnAnswerDeleteEvent;
import com.example.miha.sudocu.mvp.view.events.OnAnswerChangeEvent;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class PlayingFieldFragment extends BaseMvpFragment implements IGridView {
    private TextView[] textViewsGrid;
    private Map<Integer, Integer> textBackgroundResource = new Hashtable<>();
    private Map<Integer, TableLayout> tableLayouts = new Hashtable<>();
    private int arrIntIdGrid[] = new int[]{R.id.top_left, R.id.top_center, R.id.top_right, R.id.middle_left, R.id.middle_center, R.id.middle_right, R.id.bottom_left, R.id.bottom_center, R.id.bottom_right};

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        for (int anArrIntIdGrid : arrIntIdGrid) {
            tableLayouts.put(anArrIntIdGrid, (TableLayout) rootView.findViewById(anArrIntIdGrid));
        }
        App.getComponent().playingFragment().inject(this);
        return rootView;
    }

    @Override
    public void onResume() {
        presenterGrid.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        presenterGrid.onPause();
        super.onPause();
    }

    @Subscribe
    public void afterDeleteEvent(OnAfterAnswerDeleteEvent event) {
        presenterGrid.deleteAnswer();
    }

    @Subscribe
    public void clickOnButtonDeleteAnswer(OnAnswerDeleteEvent event) {
        presenterGrid.clearError();
    }

    @Subscribe
    public void clickOnButton(OnAnswerChangeEvent answer) {
        presenterGrid.answer();
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
    public void setFocus(Integer id, Boolean isError) {
        if (isError) {
            textViewsGrid[id].setBackgroundResource(R.drawable.focus_error);
            textViewsGrid[id].setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            textViewsGrid[id].setBackgroundResource(R.drawable.focus);
        }
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
    public void replayGame(OnAfterGameChangeEvent event){
        presenterGrid.updateUI();
    }
}