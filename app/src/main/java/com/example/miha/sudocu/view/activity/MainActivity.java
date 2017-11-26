package com.example.miha.sudocu.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miha.sudocu.DP;
import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.model.Answer;
import com.example.miha.sudocu.view.IView.IGridView;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.presenter.Service.MyMediaPlayerService;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity implements IGridView {
    private IPresenterGrid presenterGrid;
    private Timer timer;
    private TextView[] textViewsGrid;
    private Map<Integer, Integer> textBackgroundResource = new Hashtable<>();
    private Map<Integer, TableLayout> tableLayouts = new Hashtable<>();
    private Toolbar toolbar;
    private int arrIntIdGrid[] = new int[]{R.id.top_left, R.id.top_center, R.id.top_right, R.id.middle_left, R.id.middle_center, R.id.middle_right, R.id.bottom_left, R.id.bottom_center, R.id.bottom_right};
    public static String myMediaPlayer = "myMediaPlayer";

    private void initViews() {
        ButterKnife.bind(this);
        for (int anArrIntIdGrid : arrIntIdGrid) {
            tableLayouts.put(anArrIntIdGrid, (TableLayout) findViewById(anArrIntIdGrid));
        }
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
        textViewsGrid[id].setTextColor(getResources().getColor(R.color.colorAccent));//.setTextColor(R.color.colorAccent);
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

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9})
    void clickOnButton(View v) {
        String answer = ((Button) v).getText().toString();
        presenterGrid.answer(answer);
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
                int backgroundResource;
                textView.setOnClickListener(v -> presenterGrid.choseInput(v.getId()));
                backgroundResource = grid[i][j].isAnswer() ? R.drawable.background_grey : R.drawable.back;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        toolbarInit();
        presenterGrid = DP.get().getPresenterOfGrid();
        presenterGrid.init(this.getIntent());
        presenterGrid.setView(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyMediaPlayerService.class));
        presenterGrid.unSubscription();
        presenterGrid = null;
    }

    private void toolbarInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.reloadGame:
                    presenterGrid.reloadGame();
                    break;
            }
            return false;
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {//срабатывает только во время поворота устройства
        super.onSaveInstanceState(outState);
        presenterGrid.onSaveInstanceState(outState);
    }


    @Override
    public void setGameTime(long time) {
        toolbar.setSubtitle("mama");
    }


    @Override
    public void gameOver() {
        playInBackground(R.raw.applause);
        Toast.makeText(this, "game over", Toast.LENGTH_SHORT).show();
        timer = new Timer();
        // Выполняем действие с задержкой 3 секунды:
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }

    public void playInBackground(int res) {
        startService(new Intent(this, MyMediaPlayerService.class).putExtra(myMediaPlayer, res));
    }
}