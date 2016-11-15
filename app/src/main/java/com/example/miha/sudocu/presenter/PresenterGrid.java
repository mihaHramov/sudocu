package com.example.miha.sudocu.presenter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miha.sudocu.IView.IGridView;
import com.example.miha.sudocu.data.Grid;

import java.io.Serializable;

/**
 * Created by miha on 17.10.2016.
 */
public class PresenterGrid implements Serializable
{
    private final String EXTRA_MODEL="modelGrid";
    private IGridView View;
    private Grid model ;
    private String[][] grid;
    public void init(Bundle onSaved){
        if(onSaved!=null){
            model = (Grid)onSaved.getSerializable(EXTRA_MODEL);
        }
        if(model==null ){
            model = new Grid();
            model.init();
        }

        grid = model.getGrid();
        View.showGrid(grid);
    }
    public PresenterGrid(IGridView context) {
        View = context;
    }


    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_MODEL,model);
    }
    public void answer(){
        EditText editText  = View.getLastEditText();
        String g  =   View.getLastEditText().getText().toString();
        int id = editText.getId();
        int i = id/grid.length;
        int j = id%grid.length;
        if(model.getAnsver(i,j,g)){
            editText.setEnabled(false);
            View.addArrayID(id);
            Toast.makeText(View.getContext(), "success", Toast.LENGTH_SHORT).show();
            if(model.getUndefined()==0){
                Toast.makeText(View.getContext(), "game over", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(View.getContext(),"ne ok",Toast.LENGTH_SHORT).show();
        }
    }
    public Grid getGrid(){
        return model;
    }
}
