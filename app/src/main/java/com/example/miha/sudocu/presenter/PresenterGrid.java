package com.example.miha.sudocu.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.miha.sudocu.IView.IGridView;
import com.example.miha.sudocu.data.Grid;

/**
 * Created by miha on 17.10.2016.
 */
public class PresenterGrid {
    private IGridView View;
    private Grid model ;
    public PresenterGrid(IGridView context) {
        View = context;
        model = new Grid();
        View.showGrid(model.getGrid());
    }
}
