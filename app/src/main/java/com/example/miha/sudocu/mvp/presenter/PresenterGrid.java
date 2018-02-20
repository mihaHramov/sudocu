package com.example.miha.sudocu.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.data.model.Answer;
import com.example.miha.sudocu.mvp.data.model.HistoryAnswer;
import com.example.miha.sudocu.mvp.presenter.interactor.intf.IPresenterGridInteractor;
import com.example.miha.sudocu.mvp.view.intf.IGridView;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;

import java.util.ArrayList;


import rx.Scheduler;

@InjectViewState
public class PresenterGrid extends MvpPresenter<IGridView> implements IPresenterGrid {
    private IRepositoryGame repository;
    private IRepositorySettings settings;
    private Grid model;
    private Scheduler dbScheduler;
    private Scheduler mainScheduler;
    private Scheduler newScheduler;
    private IPresenterGridInteractor interactor;

    @Override
    public void replayGame() {
        model.replayGame();
        getViewState().showGrid(model.getGameGrid());
    }

    private void history(HistoryAnswer incre) {
        if (incre != null) {
            this.choseInput(incre.getAnswerId());
            this.addAnswer(incre.getAnswer(), incre.getAnswerId());
        }
    }

    @Override
    public void historyForward() {
        history(model.incrementHistory());
    }

    @Override
    public void historyBack() {
        this.deleteAnswer();
        history(model.decrementHistory());
    }

    @Override
    public void onPause() {
        repository.saveGame(model)
                .subscribeOn(dbScheduler)
                .observeOn(mainScheduler)
                .subscribe(aVoid -> {}, throwable -> Log.d("mihaHramov", throwable.getMessage()), () -> {});
    }

    @Override
    public void onResume() {
        Integer lastInputId = model.getLastChoiseField();
        getViewState().showGrid(model.getGameGrid());
        showCounterOfAnswer();

        if (lastInputId == null) return;

        ArrayList<Integer> errors = interactor.getError();
        interactor.sameAnswerMode(lastInputId, errors, integers -> getViewState().showTheSameAnswers(integers));
        interactor.knowAnswerMode(lastInputId, errors, integers -> getViewState().showKnownOptions(integers));
        getViewState().showError(errors);
        getViewState().setFocus(lastInputId, errors.contains(lastInputId));
    }

    public PresenterGrid(IRepositoryGame repository,IPresenterGridInteractor interactor,IRepositorySettings repositorySettings) {
        this.settings = repositorySettings;
        this.interactor = interactor;
        this.repository = repository;
    }

    public void setScheduler(Scheduler db, Scheduler main, Scheduler all) {
        mainScheduler = main;
        dbScheduler = db;
        newScheduler = all;
    }

    public void setModel(Grid grid) {
        this.model = grid;
        interactor.setModel(grid);
    }

    @Override
    public void reloadGame() {
        model.reloadGame().getGameGrid();
        getViewState().showGrid(model.getGameGrid());
    }

    private void addAnswer(String answer, Integer lastChoseInputId) {
        interactor.sameAnswerMode(lastChoseInputId, new ArrayList<>(), integers -> getViewState().clearTheSameAnswer(integers));
        getViewState().clearError(interactor.getError());
        model.setAnswer(lastChoseInputId, answer);//установил новый ответ
        ArrayList<Integer> error = interactor.getError();//getError();
        getViewState().showError(error);
        getViewState().setTextToAnswer(new Answer(answer, null, lastChoseInputId));

        interactor.knowAnswerMode(lastChoseInputId, error, integers -> getViewState().showKnownOptions(integers));
        interactor.sameAnswerMode(lastChoseInputId, error, integers -> getViewState().showTheSameAnswers(integers));
        showCounterOfAnswer();
        if (model.isGameOver()) {//проверка на конец игры
            getViewState().gameOver();
        }
        getViewState().setFocus(lastChoseInputId, error.contains(lastChoseInputId));
    }

    private Boolean isEmptyString(String str) {
        return str.trim().isEmpty();
    }

    public void answer(String answer) {
        Integer lastChoseInputId = model.getLastChoiseField();
        if (isEmptyString(answer) || lastChoseInputId == null || !model.isAnswer(lastChoseInputId)) {
            return;
        }

        addAnswer(answer, lastChoseInputId);
        HistoryAnswer historyAnswer = new HistoryAnswer(lastChoseInputId, answer);
        model.addAnswerToHistory(historyAnswer);
    }

    @Override
    public void deleteAnswer() {
        Integer idAnswer = model.getLastChoiseField();
        Boolean isAnswer = model.isAnswer(idAnswer);
        Answer answerForDelete = new Answer("", isAnswer, idAnswer);
        if (isAnswer) {
            getViewState().clearError(interactor.getError());
            interactor.sameAnswerMode(idAnswer, new ArrayList<>(), integers -> getViewState().clearTheSameAnswer(integers));
            model.deleteAnswer(answerForDelete);
            interactor.knowAnswerMode(idAnswer, new ArrayList<>(), integers -> getViewState().showKnownOptions(integers));
            getViewState().setTextToAnswer(answerForDelete);
            getViewState().setFocus(idAnswer, false);
            getViewState().showError(interactor.getError());
        }
    }

    public void choseInput(int id) {
        Integer lastInputId = model.getLastChoiseField();
        //проверка на повторный клик по одному и тому же полю
        if (lastInputId != null && lastInputId == id) {
            return;
        }
        ArrayList<Integer> error = interactor.getError();
        if (lastInputId != null) {
            getViewState().removeFocus(lastInputId);
            interactor.knowAnswerMode(lastInputId, error, integers -> getViewState().clearTheSameAnswer(integers));
            interactor.sameAnswerMode(lastInputId, error, integers -> getViewState().clearTheSameAnswer(integers));
        }
        interactor.sameAnswerMode(id, error, integers -> getViewState().showTheSameAnswers(integers));
        interactor.knowAnswerMode(id, error, integers -> getViewState().showKnownOptions(integers));
        getViewState().showError(error);
        getViewState().setFocus(id, error.contains(id));
        model.setLastChoiseField(id);
    }

    private void showCounterOfAnswer() {
        if (settings.getShowCountNumberOnButtonMode()) {
            getViewState().showCountOfAnswer(model.getCountOfAnswers());
        } else {
            getViewState().clearCountOfAnswer();
        }
    }
}