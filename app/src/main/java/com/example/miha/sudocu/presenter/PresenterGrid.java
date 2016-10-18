package com.example.miha.sudocu.presenter;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miha.sudocu.IView.IGridView;
import com.example.miha.sudocu.data.Grid;

/**
 * Created by miha on 17.10.2016.
 */
public class PresenterGrid {
    private IGridView View;
    private Grid model ;
    private String[][] grid;
    public PresenterGrid(IGridView context) {
        View = context;
        model = new Grid();
        grid = model.getGrid();
        View.showGrid(grid);
    }
    public void answer(){
        EditText editText  = View.getLastEditText();
        String g  =   View.getLastEditText().getText().toString();
        int id = editText.getId();
        int i = id/grid.length;
        int j = id%grid.length;
        if(model.getAnsver(i,j,g)){
            editText.setEnabled(false);
            Toast.makeText(View.getContext(), "success", Toast.LENGTH_SHORT).show();
            if(model.getUndefined()==0){
                Toast.makeText(View.getContext(), "game over", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(View.getContext(),"ne ok",Toast.LENGTH_SHORT).show();
        }
    }
}
