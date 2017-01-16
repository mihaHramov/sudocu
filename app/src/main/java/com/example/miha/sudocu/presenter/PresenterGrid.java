package com.example.miha.sudocu.presenter;

import android.content.Context;
import android.os.Bundle;

import com.example.miha.sudocu.View.IView.IGridView;
import com.example.miha.sudocu.data.Grid;
import com.example.miha.sudocu.data.SettingComplexity;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;


public class PresenterGrid implements IPresenterGrid {
    public static final String EXTRA_MODEL = "modelGrid";
    private IGridView view;
    private Grid model;
    private SettingComplexity modelOfSettings;
    private String[][] grid;

    private void initModel() {
        model = new Grid();
        model.setComplexity(modelOfSettings.load());
        model.init();
    }

    public void init(Bundle onSaved) {
        modelOfSettings = new SettingComplexity((Context) view);
        if (onSaved != null) {
            model = (Grid) onSaved.getSerializable(EXTRA_MODEL);
            //if (model.getComplexity() != modelOfSettings.load()) {//если настройки были изменены
           // initModel();
            // }
        }
        if (model == null) {
            initModel();
        }
        view.clearGrid();
        grid = model.getGrid();
        view.showGrid(grid);
    }

    public PresenterGrid(IGridView context) {
        view = context;
    }


    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_MODEL, model);
    }

    @Override
    public void unSubscription() {
        view = null;
    }

    public void answer(String answer) {
        if (answer.trim().isEmpty()) return;
        int id = view.getIdAnswer();
        if (model.getAnswer(id, answer)) {
            view.success();
            if (model.getUndefined() == 0) {
                view.gameOver();
            }
        } else {
            view.failure();
        }
    }

    public Grid getGrid() {
        return model;
    }
}