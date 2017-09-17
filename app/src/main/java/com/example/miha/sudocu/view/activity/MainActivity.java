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

import com.example.miha.sudocu.R;
import com.example.miha.sudocu.data.model.Answer;
import com.example.miha.sudocu.view.IView.IGridView;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.presenter.PresenterGrid;
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

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9})
    void clickOnButton(View v) {
        String answer = ((Button) v).getText().toString();
        if (lastEditText == null) return;
        lastEditText.setText(answer);
        presenterGrid.answer(answer);
    }

    @Override
    public IGridView showGrid(Answer[][] grid) {
        countOfRowsAndCols = grid.length;
        for (int i = 0; i < countOfRowsAndCols; i++) {
            for (int j = 0; j < countOfRowsAndCols; j++) {
                int resI = (i / 3) * 3;
                int resJ = j / 3;
                TableLayout tab = tableLayouts.get(arrIntIdGrid[resI + resJ]);//выбрал необходимый квадрат
                TableRow row = (TableRow)tab.getChildAt(i % 3);
                TextView textView = (TextView) row.getChildAt(j % 3);
                textView.setText(grid[i][j].getNumber());

                if(grid[i][j].isAnswer()){
                    textView.setOnClickListener(v -> {
                        if(lastEditText!=null){
                            lastEditText.setBackgroundResource(R.drawable.background_grey);
                        }
                        lastEditText = (TextView) v;
                        textView.setBackgroundResource(R.drawable.focus);
                    });
                    textView.setBackgroundResource(R.drawable.background_grey);
                    textView.setId(i*countOfRowsAndCols+j);
                }else{
                    textView.setOnClickListener(null);
                    textView.setBackgroundResource(R.drawable.back);
                }

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
        presenterGrid = new PresenterGrid(this);
        presenterGrid.init(savedInstanceState, this);
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

    @Override
    protected void onPause() {
        super.onPause();
        presenterGrid.savedPresenter();
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
    public int getIdAnswer() {
        return lastEditText.getId();
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