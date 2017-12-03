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

        viewState.clearTheSameAnswer();

        model.setAnswer(lastChoseInputId, answer);//установил новый ответ

        activityInfo.setErrorForClean(activityInfo.getError());//зачистил все

        viewState.clearError();//очистил ошибки

        if(settings.getErrorMode()){
            activityInfo.addError(lastChoseInputId, model.getErrors(lastChoseInputId));//выбрал ошибки для текущего
        }
        activityInfo.setLastAnswer(answer);

        viewState.showErrorInput();

        ArrayList<Integer> knowOption = model.getKnowOptions(lastChoseInputId);
        knowOption.removeAll(activityInfo.getError());
        activityInfo.setKnowOption(knowOption);

        viewState.showAnswer();
        if(settings.getKnowAnswerMode()) {
            viewState.showKnownOptions();
        }
        ArrayList<Integer> sameAnswers = model.getTheSameAnswers(activityInfo.getLastChoseInputId());
        sameAnswers.removeAll(activityInfo.getError());
        activityInfo.setSameAnswer(sameAnswers);
        if(settings.getShowSameAnswerMode()){
            viewState.showTheSameAnswers();
        }

        if (model.isGameOver()) {//проверка на конец игры
            viewState.showGameOver();
        }

        if (activityInfo.getError().contains(activityInfo.getLastChoseInputId())) {
            viewState.showErrorFocus();
        } else {
            viewState.focus();
        }
    }

    public void choseInput(int id) {
        Integer lastInputId = activityInfo.getLastChoseInputId();
        if (lastInputId != null && lastInputId == id) {//проверка на повторный клик по одному и тому же полю
            return;
        }
        ArrayList<Integer> error = activityInfo.getError();//получил ошибки

        viewState.clearKnownOptions();//убрал старые ответы
        viewState.removeFocus();      //убрал старый фокус
        viewState.clearTheSameAnswer();//убрал такие же ответы
        viewState.showErrorInput();

        if (!model.getAnswer(id).trim().isEmpty()) {//если выбраное поле не пустое
            //оговорка очищать только те что не в списке ошибок
            ArrayList<Integer> sameAnswer = model.getTheSameAnswers(id);
            sameAnswer.removeAll(error);
            activityInfo.setSameAnswer(sameAnswer);
            viewState.showTheSameAnswers();
        }

        ArrayList<Integer> knowOptions = model.getKnowOptions(id);
        knowOptions.removeAll(error);
        activityInfo.setKnowOption(knowOptions);//выбрал точно известные ответы
        activityInfo.setLastChoseInputId(id);//установить новый id

        if(settings.getKnowAnswerMode()) {
            viewState.showKnownOptions();//показать известные варианты
        }
        if (error.contains(activityInfo.getLastChoseInputId())) {
            viewState.showErrorFocus();
        } else {
            viewState.focus();
        }
    }

    //нужен для отслеживания состояния активити
    private class ViewGridState {
        private IGridView view;

        private void attachView(IGridView view) {
            this.view = view;
            showGameGrid();
            showErrorInput();
            focus();
            showErrorFocus();
            showTheSameAnswers();
            showKnownOptions();
        }


        private boolean isViewAttach() {
            return view != null;
        }


        private void removeFocus() {
            if (activityInfo.getLastChoseInputId() == null) return;
            if (isViewAttach()) {
                view.removeFocus(activityInfo.getLastChoseInputId());
            }
        }

        private void showAnswer() {
            if (activityInfo.getLastChoseInputId() == null || activityInfo.getLastAnswer() == null)
                return;
            if (isViewAttach()) {
                view.setTextToAnswer(activityInfo.getLastChoseInputId(), activityInfo.getLastAnswer());
            }
        }

        private void showKnownOptions() {
            if (isViewAttach()&&settings.getKnowAnswerMode()) {
                view.showKnownOptions(activityInfo.getKnowOption());
            }
        }

        private void showErrorInput() {
            if (activityInfo.getLastAnswer() != null && activityInfo.getLastChoseInputId() != null) {
                if (isViewAttach()&&settings.getErrorMode()) {
                    view.showError(activityInfo.getError());
                }
            }
        }

        private void clearHistory() {
            activityInfo.clear();
        }

        private void showTheSameAnswers() {
            if (isViewAttach()&&settings.getShowSameAnswerMode()) {
                view.showTheSameAnswers(activityInfo.getSameAnswer());
            }
        }

        private void focus() {
            if (activityInfo.getLastChoseInputId() == null) return;
            if (isViewAttach()) {
                view.setFocus(activityInfo.getLastChoseInputId());
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

        private void clearTheSameAnswer() {
            if (activityInfo.getLastChoseInputId() == null) return;
            if (isViewAttach()) {
                view.clearTheSameAnswer(activityInfo.getSameAnswer());
            }

        }

        private void clearKnownOptions() {
            if (activityInfo.getLastChoseInputId() == null) return;
            if (isViewAttach()) {
                view.clearKnownOptions(activityInfo.getKnowOption());
            }
        }

        private void clearError() {
            if (isViewAttach()) {
                view.clearError(activityInfo.getErrorForClean());
            }
        }

        private void showErrorFocus() {
            if (activityInfo.getLastChoseInputId() == null) return;
            if (isViewAttach()&&settings.getErrorMode()) {
                if (activityInfo.getError().contains(activityInfo.getLastChoseInputId()))
                    view.showErrorFocus(activityInfo.getLastChoseInputId());
            }
        }
    }//end viewState
}