package com.example.miha.sudocu.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.miha.sudocu.data.DP.GenerateGame;
import com.example.miha.sudocu.data.DP.MementoMainActivity;
import com.example.miha.sudocu.data.DP.intf.IRepositorySettings;
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
    private boolean saveData;
    private IRepository repository;
    private IRepositorySettings settings;
    private Grid model;
    private MementoMainActivity activityInfo;

    public PresenterGrid(IRepository repository, IRepositorySettings repositorySettings) {
        this.settings = repositorySettings;
        activityInfo = new MementoMainActivity();
        this.viewState = new ViewGridState();
        this.repository = repository;
    }


    @Override
    public void reloadGame() {
        int complex = model.getComplexity();
        model = new Grid()
                .setComplexity(complex)
                .setUndefined(complex)
                .init(new GenerateGame());
        viewState.showGameGrid();
    }

    private void initModel(int complex) {
        model = new Grid().setComplexity(complex).setUndefined(complex).init(new GenerateGame());
    }

    public void init(Intent intent) {
        if (intent.getSerializableExtra(Grid.KEY) != null && model == null) {
            model = (Grid) intent.getSerializableExtra(Grid.KEY);
        }
        if (model == null) {//если новая игра
            int complex = intent.getIntExtra(AlertDialog.SETTINGS, 1);
            initModel(complex);
        }
        saveData = false;
    }


    public void setView(IGridView view) {
        Boolean modeError = !settings.getErrorMode();
        ArrayList<Integer> errors = new ArrayList<>();

        if (!modeError) {
            errors = model.getErrors();
        }
        viewState.setErrors(errors);

        ArrayList<Integer> knowOpt = new ArrayList<>();
        if (settings.getKnowAnswerMode()) {
            knowOpt = model.getKnowOptions(viewState.getLastId());
        }
        if (!modeError) {
            knowOpt.removeAll(errors);
        }
        viewState.setKnowOption(knowOpt);

        ArrayList<Integer> sameAnswers = new ArrayList<>();
        if (settings.getShowSameAnswerMode()) {
           sameAnswers  = model.getTheSameAnswers(viewState.getLastId());
           Log.d("mihaHramov","Mode On sameAnswer has "+sameAnswers.size());
        }
        if (!modeError) {
            sameAnswers.removeAll(errors);
            Log.d("mihaHramov","sameAnswer has errors"+errors.size());
        }
        viewState.setSameAnswer(sameAnswers);

        viewState.attachView(view);
    }

    public void onSaveInstanceState(Bundle outState) {
        saveData = true;
        viewState.detachView();
    }

    @Override
    public void unSubscription() {//отписался
        if (!saveData) {
            repository.saveGame(model)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aVoid -> {
                    }, throwable -> Log.d("mihaHramov", throwable.getMessage()), () -> {
                    });
            model = null;
            viewState.clearHistory();
        }
    }

    public void answer(String answer) {
        Integer lastChoseInputId = activityInfo.getLastChoseInputId();
        if (answer.trim().isEmpty() || lastChoseInputId == null || !model.isAnswer(lastChoseInputId)) {
            return;
        }

        ArrayList<Integer> clearAnswer = model.getTheSameAnswers(lastChoseInputId);
        viewState.clearTheSameAnswer(clearAnswer);

        viewState.clearError();//очистил ошибки

        model.setAnswer(lastChoseInputId, answer);//установил новый ответ

        ArrayList<Integer> error = new ArrayList<>();
        if (settings.getErrorMode()) {
            error = model.getErrors();
            viewState.showErrorInput(error);
        }
        viewState.showAnswer(lastChoseInputId, answer);

        if (settings.getKnowAnswerMode()) {
            ArrayList<Integer> knowOption = model.getKnowOptions(lastChoseInputId);
            knowOption.removeAll(error);
            viewState.showKnownOptions(knowOption);
        }


        if (settings.getShowSameAnswerMode()) {
            ArrayList<Integer> sameAnswers = model.getTheSameAnswers(lastChoseInputId);
            sameAnswers.removeAll(error);
            viewState.showTheSameAnswers(sameAnswers);
        }

        if (model.isGameOver()) {//проверка на конец игры
            viewState.showGameOver();
        }

        if (error.contains(lastChoseInputId)) {
            viewState.showErrorFocus(lastChoseInputId);
        } else {
            viewState.focus(lastChoseInputId);
        }
    }

    public void choseInput(int id) {
        Integer lastInputId = activityInfo.getLastChoseInputId();
        if (lastInputId != null && lastInputId == id) {//проверка на повторный клик по одному и тому же полю
            return;
        }
        ArrayList<Integer> error = new ArrayList<>();
        if (settings.getErrorMode()) {
            error = model.getErrors();//получил ошибки
        }
        if (lastInputId != null) {
            ArrayList clearKnowOption = model.getKnowOptions(lastInputId);
            clearKnowOption.removeAll(error);
            viewState.clearKnownOptions(clearKnowOption);//убрал старые ответы
            viewState.removeFocus(lastInputId);      //убрал старый фокус

            ArrayList<Integer> clearSameAnswer = model.getTheSameAnswers(lastInputId);
            clearSameAnswer.removeAll(error);
            viewState.clearTheSameAnswer(clearSameAnswer);//убрал такие же ответы
        }


        if (settings.getErrorMode()) {
            viewState.showErrorInput(error);//отобразил ошибки
        }

        if (!model.getAnswer(id).trim().isEmpty()) {
            ArrayList<Integer> sameAnswer = model.getTheSameAnswers(id);
            if (settings.getErrorMode()) {
                sameAnswer.removeAll(error);
            }
            viewState.showTheSameAnswers(sameAnswer);
        }


        if (settings.getKnowAnswerMode()) {
            ArrayList<Integer> knowOptions = model.getKnowOptions(id);
            if (settings.getErrorMode()) {
                knowOptions.removeAll(error);
            }
            viewState.showKnownOptions(knowOptions);//показать известные варианты
        }

        if (error.contains(id)) {
            viewState.showErrorFocus(id);
        } else {
            viewState.focus(id);
        }
        activityInfo.setLastChoseInputId(id);
    }

    //нужен для отслеживания состояния активити
    private class ViewGridState {
        private IGridView view;
        private Integer lastId;
        private ArrayList<Integer> sameAnswer;
        private ArrayList<Integer> knowOption;
        private ArrayList<Integer> errors;

        public Integer getLastId() {
            return lastId;
        }

        public void setLastId(Integer lastid) {
            this.lastId = lastid;
        }

        public ViewGridState() {
            sameAnswer = new ArrayList<>();
            knowOption = new ArrayList<>();
            errors = new ArrayList<>();
        }

        public void setSameAnswer(ArrayList<Integer> sameAnswer) {
            this.sameAnswer = sameAnswer;
        }

        public void setKnowOption(ArrayList<Integer> knowOption) {
            this.knowOption = knowOption;
        }

        public void setErrors(ArrayList<Integer> errors) {
            this.errors = errors;
        }

        private void attachView(IGridView view) {
            this.view = view;
            showGameGrid();
            clearError();
            showErrorInput(errors);
            focus(lastId);
            showErrorFocus(lastId);
            showTheSameAnswers(sameAnswer);
            showKnownOptions(knowOption);
        }


        private boolean isViewAttach() {
            return view != null;
        }


        private void removeFocus(Integer id) {
            if (isViewAttach()) {
                view.removeFocus(id);
            }
        }

        private void showAnswer(Integer id, String answer) {
            if (isViewAttach()) {
                view.setTextToAnswer(id, answer);
            }
        }

        private void showKnownOptions(ArrayList<Integer> ar) {
            knowOption = ar;
            if (isViewAttach()) {
                view.showKnownOptions(ar);
            }
        }

        private void showErrorInput(ArrayList<Integer> er) {
            errors = er;
            if (isViewAttach()) {
                view.showError(errors);
            }

        }

        private void clearHistory() {
            errors = null;
            sameAnswer = null;
            lastId = null;
            knowOption = null;
            activityInfo.clear();
        }

        private void showTheSameAnswers(ArrayList<Integer> ar) {
            sameAnswer = ar;
            if (isViewAttach()) {
                view.showTheSameAnswers(ar);
            }
        }

        private void focus(Integer id) {
            if (id == null) return;
            lastId = id;
            if (isViewAttach()) {
                view.setFocus(id);
            }
        }

        private void showGameOver() {
            if (isViewAttach()) {
                view.gameOver();
            }
        }

        //показать игровое поле
        private void showGameGrid() {
            if (isViewAttach()) {
                view.showGrid(model.getGameGrid());
            }
        }

        private void detachView() {
            this.view = null;
        }

        private void clearTheSameAnswer(ArrayList<Integer> arr) {
            if (isViewAttach()) {
                view.clearTheSameAnswer(arr);
            }
        }

        private void clearKnownOptions(ArrayList<Integer> arr) {
            if (isViewAttach()) {
                view.clearKnownOptions(arr);
            }
        }

        private void clearError() {
            if (isViewAttach()) {
                view.clearError(model.getErrors());
            }
        }

        private void showErrorFocus(Integer id) {
            if (isViewAttach() && settings.getErrorMode()) {
                if (model.getErrors().contains(id)) {
                    view.showErrorFocus(id);
                }
            }
        }
    }//end viewState
}