package com.example.miha.sudocu;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.miha.sudocu.IView.IGridView;
import com.example.miha.sudocu.data.Grid;
import com.example.miha.sudocu.presenter.PresenterGrid;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements IGridView,View.OnFocusChangeListener,TextWatcher{
    private final String KEY_DATA = "dataKey";
    private PresenterGrid presenterGrid;
    private GridView gvMain;
    private ArrayAdapter<String> adapter;
    private EditText lastEditText;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if((Grid)savedInstanceState.getSerializable(KEY_DATA)==null){
            Toast.makeText(MainActivity.this, "no", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "yes", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Integer> arrayId;
    TableLayout table;
    public void addArrayID(int i){
      //  arrayId.add(i);
      //  Log.d("err", arrayId.get(0).toString());
    }
    public Context getContext(){
        return this;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v instanceof  EditText && v.isEnabled() && hasFocus){
            lastEditText = (EditText)v;
            lastEditText.addTextChangedListener(this);
        }
    }

    public EditText getLastEditText(){
        return lastEditText;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenterGrid = new PresenterGrid(this);
    }

    @Override
    public void afterTextChanged(Editable s) {}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_DATA,presenterGrid.getGrid());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        presenterGrid.answer();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
}