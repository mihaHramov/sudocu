package com.example.miha.sudocu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.miha.sudocu.View.IView.IGridView;
import com.example.miha.sudocu.View.Settings;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.presenter.PresenterGrid;

public class MainActivity extends Activity implements IGridView, View.OnFocusChangeListener {
    private IPresenterGrid presenterGrid;
    private EditText lastEditText;
    TableLayout table;
    Bundle saved;
    private Toolbar toolbar;
    private  TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenterGrid.answer(lastEditText.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };


    @Override
    public void clearGrid() {
        table.removeAllViews();
        Toast.makeText(MainActivity.this, "clear", Toast.LENGTH_SHORT).show();
    }

    private void initViews(){
        table = (TableLayout) findViewById(R.id.tableLayout1);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v instanceof EditText && v.isEnabled()) {
            if(hasFocus){
                lastEditText = (EditText) v;
                lastEditText.addTextChangedListener(textWatcher);
            }else{
                lastEditText.removeTextChangedListener(textWatcher);
            }
        }
    }

    @Override
    public int getIdAnswer() {
        return lastEditText.getId();
    }

    public void showGrid(String[][] grid) {

        for (int i = 0; i < grid.length; i++) {
            TableRow row = new TableRow(this);
            for (int j = 0; j < grid.length; j++) {
                EditText text = new EditText(this);
                text.setId(i * grid.length + j);
                text.setText(grid[i][j]);
                text.setEnabled(grid[i][j].isEmpty());
                text.setOnFocusChangeListener(this);
                row.addView(text);
            }
            table.addView(row);
        }
    }


    @Override
    public void failure() {
        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saved = savedInstanceState;
        Log.d("logActivity","onCreate" );
        setContentView(R.layout.activity_main);
        presenterGrid = new PresenterGrid(this);
        initViews();
        toolbarInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("logActivity", "onResume");
        presenterGrid.init(saved);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenterGrid.onSaveInstanceState(outState);
    }

    @Override
    public void success() {
        lastEditText.setEnabled(false);
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterGrid.unSubscription();
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
}