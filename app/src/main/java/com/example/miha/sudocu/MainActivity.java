package com.example.miha.sudocu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.example.miha.sudocu.View.Settings;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.presenter.PresenterGrid;


public class MainActivity extends Activity implements IGridView, View.OnClickListener {
    private IPresenterGrid presenterGrid;
    private TextView lastEditText;
    TableLayout table;
    private Toolbar toolbar;

    @Override
    public void clearGrid() {
        table.removeAllViews();
    }

    private void initViews() {
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        table = (TableLayout) findViewById(R.id.tableLayout1);
       // Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
       // hronometer.setBase(SystemClock.elapsedRealtime());
       // chronometer.start();
    }


    @Override
    public int getIdAnswer() {
        return lastEditText.getId();
    }

    public void showGrid(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            TableRow row = new TableRow(this);
            for (int j = 0; j < grid.length; j++) {
                TextView text = new TextView(this);
                text.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                text.setId(i * grid.length + j);
                text.setText(grid[i][j]);
                if (grid[i][j].isEmpty()) {
                    text.setOnClickListener(this);
                    //  text.setBackground(getResources().getDrawable(R.drawable.back));
                    // text.setTextAppearance(this,R.style.bigBorder);
                }
                row.addView(text);
            }
            table.addView(row);
        }
    }


    @Override
    public void failure() {
        lastEditText.setBackgroundColor(Color.RED);
        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenterGrid.unSubscription();

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
    protected void onSaveInstanceState(Bundle outState) {//срабатывает только во время поворота устройства
        super.onSaveInstanceState(outState);
        presenterGrid.onSaveInstanceState(outState);
    }

    @Override
    public void success() {
        lastEditText.setBackgroundColor(Color.GREEN);
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
       // presenterGrid.unSubscription();
        super.onDestroy();
    }

    private void toolbarInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
                return false;
            }
        });
    }


    @Override
    public void gameOver() {
        Toast.makeText(this, "game over", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            String answer = ((Button) v).getText().toString();
            lastEditText.setText(answer);
            presenterGrid.answer(answer);
            return;
        }
        if (lastEditText != null) {
            lastEditText.setBackgroundDrawable(null);
        }
        lastEditText = (TextView) v;
        lastEditText.setBackgroundDrawable(getResources().getDrawable(R.drawable.back));
        // lastEditText.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }
}