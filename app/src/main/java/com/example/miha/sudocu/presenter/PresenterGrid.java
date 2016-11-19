package com.example.miha.sudocu.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.miha.sudocu.MainActivity;
import com.example.miha.sudocu.View.IView.IGridView;
import com.example.miha.sudocu.data.Grid;
import com.example.miha.sudocu.data.SettingComplexity;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;


public class PresenterGrid implements IPresenterGrid {
    private final String EXTRA_MODEL = "modelGrid";
    private IGridView View;
    private Grid model;
    private SettingComplexity modelOfSettings;
    private String[][] grid;

    private void initModel() {
        model = new Grid();
        model.setComplexity(modelOfSettings.load());
        model.init();
    }

    public void init(Bundle onSaved) {
        modelOfSettings = new SettingComplexity((Context) View);
        if (onSaved != null) {
            model = (Grid) onSaved.getSerializable(EXTRA_MODEL);
            if (model.getComplexity() != modelOfSettings.load()) {//если настройки были изменены
                initModel();
            }
        }
        if (model == null) {
            initModel();
        }
        View.clearGrid();
        grid = model.getGrid();
        View.showGrid(grid);
    }

    public PresenterGrid(IGridView context) {
        View = context;
    }


    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_MODEL, model);
    }

    @Override
    public void unSubscription() {
        View = null;
    }

    public void answer(String s) {
        if (s.trim().isEmpty()) return;
        int id = View.getIdAnswer();
        if (model.getAnswer(id, s)) {
            View.success();
            if (model.getUndefined() == 0) {
                View.gameOver();
            }
        } else {
            View.failure();
        }
    }

    public Grid getGrid() {
        return model;
    }
}