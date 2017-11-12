package com.example.miha.sudocu.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
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

import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity implements IGridView {
    private IPresenterGrid presenterGrid;
    private TextView lastEditText;
    private Chronometer chronometer;
    private int countOfRowsAndCols;
    private Timer timer;
    private TextView[][] textViewsGrid;
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
        chronometer = (Chronometer) findViewById(R.id.chronometer);
    }

    @Override
    public void focusInput(int id) {
        int i = id / countOfRowsAndCols, j = id % countOfRowsAndCols;
        TextView v = textViewsGrid[i][j];
        v.setBackgroundResource(R.drawable.focus);
        lastEditText = v;
    }

    @Override
    public void showKnownOptions(int id) {
        if (lastEditText != null) {//очистка прошлого выбраного варианта
            int idEditText = lastEditText.getId();
            int i = idEditText / countOfRowsAndCols, j = idEditText % countOfRowsAndCols;
            showAnswerInRectangle(getRectangle(i,j),R.drawable.back);
            int count = 0;
            while (count < countOfRowsAndCols) {
                if (count != j) {
                    int textBackground = textBackgroundResource.get(i * countOfRowsAndCols + count);
                    showOneAnswer(i, count, textBackground);
                }
                count++;
            }

            count = 0;
            while (count < countOfRowsAndCols) {
                if (count != i) {
                    int textBackground = textBackgroundResource.get(count * countOfRowsAndCols + j);
                    showOneAnswer(count, j, textBackground);
                }
                count++;
            }
            lastEditText.setBackgroundResource(R.drawable.background_grey);
        }
        showAllAnswer(id, R.drawable.show_all_know_answer);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9})
    void clickOnButton(View v) {
        if (lastEditText == null) return;
        String answer = ((Button) v).getText().toString();
        lastEditText.setText(answer);
        presenterGrid.answer(answer,lastEditText.getId());
    }

    public void showOneAnswer(int i, int count, int res) {//сюда
        TextView tempText = textViewsGrid[i][count];
        if (textBackgroundResource.get(tempText.getId()).equals(R.drawable.back)) {
            textViewsGrid[i][count].setBackgroundResource(res);
        }
    }
    public int getRectangle(int i,int j){
        return (i / 3) * 3 + j / 3;
    }

    public void showAllAnswer(int id, int res) {
        int i = id / countOfRowsAndCols, j = id % countOfRowsAndCols;
        int count = 0;
        while (count < countOfRowsAndCols) {//покрасил все в строке
            if (count != j)
                showOneAnswer(i, count, res);
            count++;
        }
        count = 0;
        while (count < countOfRowsAndCols) {//покрасил все в столбце
            if (count != i)
                showOneAnswer(count, j, res);
            count++;
        }
        showAnswerInRectangle(getRectangle(i,j),res);//покрасил все в квадрате
    }

    public void showAnswerInRectangle(int rectangle,int res) {
        TableLayout tableLayout = tableLayouts.get(arrIntIdGrid[rectangle]);
        for (int k = 0; k < tableLayout.getChildCount(); k++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(k);
            for (int n = 0; n < tableRow.getChildCount(); n++) {
                TextView tempText = (TextView) tableRow.getChildAt(n);
                //если это ответ то красим в указаный цвет
                if (textBackgroundResource.get(tempText.getId()).equals(R.drawable.back)) {
                    tempText.setBackgroundResource(res);
                }
            }
        }

    }

    @Override
    public IGridView showGrid(Answer[][] grid) {
        countOfRowsAndCols = grid.length;
        textViewsGrid = new TextView[countOfRowsAndCols][countOfRowsAndCols];
        for (int i = 0; i < countOfRowsAndCols; i++) {
            for (int j = 0; j < countOfRowsAndCols; j++) {
                int resI = (i / 3) * 3;
                int resJ = j / 3;
                TableLayout tab = tableLayouts.get(arrIntIdGrid[resI + resJ]);//выбрал необходимый квадрат
                TableRow row = (TableRow) tab.getChildAt(i % 3);
                TextView textView = (TextView) row.getChildAt(j % 3);
                textView.setText(grid[i][j].getNumber());
                textView.setId(i * countOfRowsAndCols + j);
                textViewsGrid[i][j] = textView;
                int backgroundResource;
                if (grid[i][j].isAnswer()) {
                    backgroundResource = R.drawable.background_grey;
                    textView.setOnClickListener(v -> presenterGrid.choseInput(v.getId()));
                } else {
                    backgroundResource = R.drawable.back;
                    textView.setOnClickListener(v -> presenterGrid.choseNotInput(v.getId()));
                }
                textView.setBackgroundResource(backgroundResource);
                textBackgroundResource.put(i * countOfRowsAndCols + j, backgroundResource);
            }
        }
        return this;
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


    @Override
    protected void onResume() {
        super.onResume();
        presenterGrid.loadGameTime();
    }

    private void toolbarInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.navigateButtonBack:
                    finish();
                    break;
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
        chronometer.setBase(time);
        chronometer.start();
    }

    @Override
    public long getGameTime() {
        chronometer.stop();
        return chronometer.getBase();
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