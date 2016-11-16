package com.example.miha.sudocu.presenter;

import android.os.Bundle;

import com.example.miha.sudocu.IView.IGridView;
import com.example.miha.sudocu.data.Grid;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;


public class PresenterGrid implements IPresenterGrid {
    private final String EXTRA_MODEL = "modelGrid";
    private IGridView View;
    private Grid model;
    private String[][] grid;

    public void init(Bundle onSaved) {
        if (onSaved != null) {
            model = (Grid) onSaved.getSerializable(EXTRA_MODEL);
        }
        if (model == null) {
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
        outState.putSerializable(EXTRA_MODEL, model);
    }

    @Override
    public void unSubscription() {
        View = null;
    }

    public void answer(String s) {
        if (s.isEmpty()) return;
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
