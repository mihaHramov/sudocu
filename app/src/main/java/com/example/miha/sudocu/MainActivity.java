package com.example.miha.sudocu;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miha.sudocu.View.IView.IGridView;
import com.example.miha.sudocu.data.AudioPlayer;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.presenter.PresenterGrid;


public class MainActivity extends Activity implements IGridView, View.OnClickListener {
    private IPresenterGrid presenterGrid;
    private AudioPlayer mPlayer;
    private TextView lastEditText;
    private Chronometer chronometer;
    private int countOfRowsAndCols;
    private boolean lastAnswer = true;
    TableLayout table;
    private Toolbar toolbar;


    private void initViews() {
        int arrIntId[] = new int[]{R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};
        for (int i = 0; i < arrIntId.length; i++)
            findViewById(arrIntId[i]).setOnClickListener(this);
        table = (TableLayout) findViewById(R.id.tableLayout1);
        chronometer = (Chronometer) findViewById(R.id.chronometer);

    }

    private void DrawBorderForElements(TextView textView) {
        int i, j, id;
        id = textView.getId();
        i = id / countOfRowsAndCols;
        j = id % countOfRowsAndCols;
        if (i % 3 == 0 && i > 0) {
            textView.setBackgroundResource(R.drawable.border_top);
        } else if (j % 3 == 0 && j > 0) {
            textView.setBackgroundResource(R.drawable.border_right);
        } else if ((j % 3 == 0 && j > 0) && (i % 3 == 0 && i > 0)) {
            textView.setBackgroundResource(R.drawable.border_right_top);
        } else {
            textView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public IGridView showGrid(String[][] grid) {
        countOfRowsAndCols = grid.length;
        for (int i = 0; i < countOfRowsAndCols; i++) {
            TableRow row = new TableRow(this);
            for (int j = 0; j < countOfRowsAndCols; j++) {
                TextView text = (TextView) getLayoutInflater().inflate(R.layout.text_view, null);
                text.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                text.setId(i * countOfRowsAndCols + j);
                text.setText(grid[i][j]);
                DrawBorderForElements(text);

                if (grid[i][j].isEmpty()) {
                    text.setOnClickListener(this);
                }
                row.addView(text);
            }
            table.addView(row);
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
        mPlayer = new AudioPlayer(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
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
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigateButtonBack) {
                    finish();
                }
                return false;
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {//срабатывает только во время поворота устройства
        super.onSaveInstanceState(outState);
        presenterGrid.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            String answer = ((Button) v).getText().toString();
            if (lastEditText == null) return;
            lastEditText.setText(answer);
            presenterGrid.answer(answer);
            return;
        }


        if (v instanceof TextView) {
            if (lastEditText != null) {
                Toast.makeText(this, "" + lastEditText.getId(), Toast.LENGTH_SHORT).show();
                if(lastAnswer){
                    DrawBorderForElements(lastEditText);
                }
                lastAnswer = true;
            }
            lastEditText = (TextView) v;
            lastEditText.setBackgroundResource(R.drawable.back);
            return;
        }
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
    public IGridView clearGrid() {
        table.removeAllViews();
        return this;
    }


    @Override
    public int getIdAnswer() {
        return lastEditText.getId();
    }

    @Override
    public void failure() {
        mPlayer.play(R.raw.applause);
        lastEditText.setBackgroundColor(Color.RED);
        lastAnswer = false;
    }

    @Override
    public void success() {
        mPlayer.play(R.raw.success);
        lastAnswer = true;
        lastEditText.setOnClickListener(null);
        DrawBorderForElements(lastEditText);
        lastEditText = null;
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void gameOver() {
        mPlayer.play(R.raw.applause);
        Toast.makeText(this, "game over", Toast.LENGTH_SHORT).show();

    }

}