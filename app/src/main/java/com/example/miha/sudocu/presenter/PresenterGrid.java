package com.example.miha.sudocu.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;

import com.example.miha.sudocu.View.IView.IGridView;
import com.example.miha.sudocu.View.MenuActivity;
import com.example.miha.sudocu.data.Grid;
import com.example.miha.sudocu.data.IRepository;
import com.example.miha.sudocu.data.RepositoryImplBD;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;


public class PresenterGrid implements IPresenterGrid {
    public static final String EXTRA_MODEL = "modelGrid";
    private IRepository repository;
    private IGridView view;
    private Grid model;
    private Activity activity;

    private void initModel() {
        int complex = activity.getIntent().getIntExtra(MenuActivity.SETTINGS, 1);
        model = new Grid().setComplexity(complex).setUndefined(complex).init();
    }

    @Override
    public void loadGameTime() {
        view.setGameTime(SystemClock.elapsedRealtime() - model.getGameTime());
    }

    public void init(Bundle onSaved, Activity activity) {
        repository = new RepositoryImplBD(activity);
        this.activity = activity;
        if (onSaved != null) {//если объект не пуст
            model = (Grid) onSaved.getSerializable(EXTRA_MODEL);
        } else {//если объет пуст(только пришли на активити)
            model = (Grid) activity.getIntent().getSerializableExtra(Grid.KEY);
        }

        if (model == null) {
            initModel();
        }

        view.clearGrid().showGrid(model.getGrid());
        loadGameTime();
    }

    public PresenterGrid(IGridView view) {
        this.view = view;
    }


    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_MODEL, model);
    }

    @Override
    public void savedPresenter() {
        model.setGameTime(SystemClock.elapsedRealtime() - view.getGameTime());
        repository.saveGame(model);
    }

    @Override
    public void unSubscription() {
        view = null;
        model = null;
    }//отписался

    public void answer(String answer) {
        if (answer.trim().isEmpty()) return;
        int id = view.getIdAnswer();
        if (model.getAnswer(id, answer)) {
            view.success();
            if (model.getUndefined() == 0) {
                model.setGameTime(view.getGameTime());
                view.gameOver();
            }
            return;
        }
        view.failure();
    }
}