package com.example.miha.sudocu;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.miha.sudocu.IView.IGridView;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;
import com.example.miha.sudocu.presenter.PresenterGrid;

public class MainActivity extends Activity implements IGridView,View.OnFocusChangeListener,TextWatcher{
    private IPresenterGrid presenterGrid;
    private EditText lastEditText;
    TableLayout table;

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v instanceof  EditText && v.isEnabled() && hasFocus){
            lastEditText = (EditText)v;
            lastEditText.addTextChangedListener(this);
        }
    }

    @Override
    public int getIdAnswer() {
        return lastEditText.getId();
    }

    public void showGrid(String[][] grid){
        table = (TableLayout)findViewById(R.id.tableLayout1);
        for(int i=0;i<grid.length;i++){
            TableRow row = new TableRow(this);
            for(int j=0;j<grid.length;j++){
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
        Toast.makeText(this,"ne ok",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenterGrid = new PresenterGrid(this);
        presenterGrid.init(savedInstanceState);
    }

    @Override
    public void afterTextChanged(Editable s) {}

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
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        presenterGrid.answer(lastEditText.getText().toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
    //    presenterGrid.unSubscription();
    }

    @Override
    public void gameOver() {
        Toast.makeText(this, "game over", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
}