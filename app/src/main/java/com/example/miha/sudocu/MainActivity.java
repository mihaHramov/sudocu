package com.example.miha.sudocu;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.miha.sudocu.IView.IGridView;
import com.example.miha.sudocu.presenter.PresenterGrid;
public class MainActivity extends Activity implements IGridView{
    private PresenterGrid presenterGrid;
    private GridView gvMain;
    private ArrayAdapter<String> adapter;



    public void showGrid(String[][] grid){
        for(int i=0;i<grid.length;i++){
            String temp = "";
            for(int j=0;j<grid.length;j++){
                temp += " "+ grid[i][j];
            }
            Log.d("array",temp);
        }
        // а вот как выводить двумерный массив я хз
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenterGrid = new PresenterGrid(this);
    }
}