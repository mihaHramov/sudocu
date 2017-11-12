package com.example.miha.sudocu.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.miha.sudocu.DP;
import com.example.miha.sudocu.data.DP.GenerateGame;
import com.example.miha.sudocu.presenter.Adapter.AlertDialog;
import com.example.miha.sudocu.view.IView.IGridView;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.DP.IRepository;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PresenterGrid implements IPresenterGrid {
    private ViewGridState viewState;
    private int idFocusInputTextView;
    private boolean saveData;
    private IRepository repository;
    private Grid model;
    private Intent intent;

    @Override
    public void choseNotInput(int id) {

    }

    @Override
    public void reloadGame() {
        int complex = model.getComplexity();
        model = new Grid()
                .setComplexity(complex)
                .setUndefined(complex)
                .init(new GenerateGame());
        viewState.showGameGrid();
        loadGameTime();
    }

    private void initModel() {
        int complex = intent.getIntExtra(AlertDialog.SETTINGS, 1);
        model = new Grid().setComplexity(complex).setUndefined(complex).init(new GenerateGame());
    }

    @Override
    public void loadGameTime() {
    }

    public void init(Intent intent) {
        saveData = false;
        this.intent = intent;
        if (intent.getSerializableExtra(Grid.KEY) != null) {//если пришел с екрана не доигранных игр
            model = (Grid) intent.getSerializableExtra(Grid.KEY);
        }
        if (model == null) {//если новая игра
            initModel();
            viewState.showGameGrid();
        }
    }

    public PresenterGrid(IRepository repository) {
        this.viewState = new ViewGridState();
        this.repository = repository;
    }

    public void setView(IGridView view) {
        viewState.attachView(view);
    }

    public void onSaveInstanceState(Bundle outState) {
        saveData = true;
        viewState.detachView();
    }

    @Override
    public void unSubscription() {
        if (!saveData) {
            repository.saveGame(model)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aVoid -> {}, throwable -> Log.d("mihaHramov", throwable.getMessage()), () -> Log.d("mihaHramov", "saveComplete"));
            model = null;
            viewState.clearHistory();
        }
    }//отписался

    public void answer(String answer, int id) {
        if (answer.trim().isEmpty()) return;
        if (model.getAnswer(id, answer)) {
            viewState.showGameOver();
        }
    }

    public void choseInput(int id) {
        idFocusInputTextView = id;
        if (true) {
            viewState.showKnownOptions();
        }
        viewState.focus();
    }

    public class ViewGridState {
        private ArrayList<Integer> myList = new ArrayList<>();
        private IGridView view;
        private final int SHOW_GRID = 0;
        private final int SHOW_KNOW_OPTIONS = 1;
        private final int FOCUS_INPUT = 2;
        private static final int SHOW_GAME_OVER = 3;

        public boolean isViewAttach() {
            return view != null;
        }

        public void showKnownOptions() {
            if (isViewAttach()) {
                view.showKnownOptions(idFocusInputTextView);
            }
            addToList(SHOW_KNOW_OPTIONS);
        }

        public void clearHistory(){
            myList.clear();
        }
        public void focus() {
            if (isViewAttach()) {
                view.focusInput(idFocusInputTextView);
            }
            addToList(FOCUS_INPUT);
        }

        public void showGameOver(){
            if (isViewAttach()) {
                view.gameOver();
            }
            addToList(SHOW_GAME_OVER);
        }

        //показать игровое поле
        public void showGameGrid() {
            if (isViewAttach()) {
                view.showGrid(model.getGameGrid());
            }
            addToList(SHOW_GRID);
        }
        public void addToList(int k){
            if(myList.contains(k)){
                myList.set(k,k);
            }else {
                myList.add(k,k);
            }
        }
        public IGridView getView() {
            return view;
        }

        public void attachView(IGridView view) {
            this.view = view;
            for (Integer i : myList) {
                switch (i) {
                    case SHOW_GRID:
                        view.showGrid(model.getGameGrid());
                        break;
                    case FOCUS_INPUT:
                        view.focusInput(idFocusInputTextView);
                        break;
                    case SHOW_KNOW_OPTIONS:
                        view.showKnownOptions(idFocusInputTextView);
                        break;

                }
            }
        }

        public void detachView() {
            this.view = null;
        }
    }
}