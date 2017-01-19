package com.example.miha.sudocu.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.miha.sudocu.View.IView.IGridView;
import com.example.miha.sudocu.data.Grid;
import com.example.miha.sudocu.data.IRepository;
import com.example.miha.sudocu.data.RepositoryImplBD;
import com.example.miha.sudocu.data.SettingComplexity;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;



public class PresenterGrid implements IPresenterGrid {
    public static final String EXTRA_MODEL = "modelGrid";
    private IRepository repository;
    private IGridView view;
    private Grid model;
    private SettingComplexity modelOfSettings;
    private String[][] grid;

    private void initModel() {
        model = new Grid();
        model.setComplexity(modelOfSettings.load());
        model.init();
    }

    public void init(Bundle onSaved, Activity activity) {
        modelOfSettings = new SettingComplexity(activity);
        repository = new RepositoryImplBD(activity);
        if (onSaved != null) {//если объект не пуст
            model = (Grid) onSaved.getSerializable(EXTRA_MODEL);
        } else {//если объет пуст(только пришли на активити)
            model = (Grid) activity.getIntent().getSerializableExtra(Grid.KEY);
        }

        if (model == null) {
            initModel();
        }

        view.clearGrid();
        grid = model.getGrid();
        view.showGrid(grid);
    }

    public PresenterGrid(IGridView view) {
        this.view = view;
    }


    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_MODEL, model);
    }

    @Override
    public void unSubscription() {
        view = null;
    }//отписался

    public void answer(String answer) {
        if (answer.trim().isEmpty()) return;
        int id = view.getIdAnswer();
        if (model.getAnswer(id, answer)) {
            view.success();
            repository.saveGame(model);
            if (model.getUndefined() == 0) {
                view.gameOver();
            }
        } else {
            view.failure();
        }
    }
}