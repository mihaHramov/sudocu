package com.example.miha.sudocu.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.data.model.Answer;
import com.example.miha.sudocu.mvp.data.model.HistoryAnswer;
import com.example.miha.sudocu.mvp.view.intf.IGridView;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action1;

@InjectViewState
public class PresenterGrid extends MvpPresenter<IGridView> implements IPresenterGrid {
    private Subscription subscription;
    private IRepositoryGame repository;
    private IRepositorySettings settings;
    private Grid model;
    private Scheduler dbScheduler;
    private Scheduler newScheduler;
    private Scheduler mainScheduler;

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
        subscription.unsubscribe();
    }

    @Override
    public void onResume() {
        Integer lastInputId = model.getLastChoiseField();
        getViewState().setGameName(model.getName());
        getViewState().showGrid(model.getGameGrid());
        showCounterOfAnswer();
        if (subscription == null || subscription.isUnsubscribed()) {
            subscription = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .subscribeOn(newScheduler)
                    .observeOn(mainScheduler)
                    .subscribe(aLong -> {
                        getViewState().setGameTime(model.getGameTime());
                        model.setGameTime(model.getGameTime() + 1);
                    });
        }
        if (lastInputId == null) return;

        ArrayList<Integer> errors = getError();
        sameAnswerMode(lastInputId, errors, integers -> getViewState().showTheSameAnswers(integers));
        knowAnswerMode(lastInputId, errors, integers -> getViewState().showKnownOptions(integers));
        getViewState().showError(errors);
        getViewState().setFocus(lastInputId, errors.contains(lastInputId));
    }

    public PresenterGrid(IRepositoryGame repository, IRepositorySettings repositorySettings) {
        this.settings = repositorySettings;
        this.repository = repository;
    }

    public void setScheduler(Scheduler db, Scheduler main, Scheduler all) {
        mainScheduler = main;
        dbScheduler = db;
        newScheduler = all;
    }

    public void setModel(Grid grid) {
        this.model = grid;
    }

    @Override
    public void reloadGame() {
        model.reloadGame().getGameGrid();
        getViewState().showGrid(model.getGameGrid());
    }

    private void addAnswer(String answer, Integer lastChoseInputId) {
        knowAnswerMode(lastChoseInputId, new ArrayList<>(), integers -> getViewState().clearTheSameAnswer(integers));
        getViewState().clearError(getError());
        model.setAnswer(lastChoseInputId, answer);//установил новый ответ
        ArrayList<Integer> error = getError();
        getViewState().showError(error);
        getViewState().setTextToAnswer(new Answer(answer, null, lastChoseInputId));

        knowAnswerMode(lastChoseInputId, error, integers -> getViewState().showKnownOptions(integers));
        sameAnswerMode(lastChoseInputId, error, integers -> getViewState().showTheSameAnswers(integers));
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
            getViewState().clearError(getError());
            sameAnswerMode(idAnswer, new ArrayList<>(), integers -> getViewState().clearTheSameAnswer(integers));
            model.deleteAnswer(answerForDelete);
            knowAnswerMode(idAnswer, new ArrayList<>(), integers -> getViewState().showKnownOptions(integers));
            getViewState().setTextToAnswer(answerForDelete);
            getViewState().setFocus(idAnswer, false);
            getViewState().showError(getError());
        }
    }

    public void choseInput(int id) {
        Integer lastInputId = model.getLastChoiseField();
        //проверка на повторный клик по одному и тому же полю
        if (lastInputId != null && lastInputId == id) {
            return;
        }
        ArrayList<Integer> error = getError();
        if (lastInputId != null) {
            getViewState().removeFocus(lastInputId);
            knowAnswerMode(lastInputId, error, integers -> getViewState().clearTheSameAnswer(integers));
            sameAnswerMode(lastInputId, error, integers -> getViewState().clearTheSameAnswer(integers));
        }
        sameAnswerMode(id, error, integers -> getViewState().showTheSameAnswers(integers));
        knowAnswerMode(id, error, integers -> getViewState().showKnownOptions(integers));
        getViewState().showError(error);
        getViewState().setFocus(id, error.contains(id));
        model.setLastChoiseField(id);
    }

    private void sameAnswerMode(int id, ArrayList<Integer> error, Action1<ArrayList<Integer>> action1) {
        if (settings.getShowSameAnswerMode() && !isEmptyString(model.getAnswer(id))) {
            ArrayList<Integer> sameAnswer = model.getTheSameAnswers(id);
            sameAnswer.removeAll(error);
            action1.call(sameAnswer);
        }
    }

    private void knowAnswerMode(Integer id, ArrayList<Integer> error, Action1<ArrayList<Integer>> action1) {
        if (settings.getKnowAnswerMode()) {
            ArrayList<Integer> knowOptions = model.getKnowOptions(id);
            knowOptions.removeAll(error);
            action1.call(knowOptions);
        }
    }

    private void showCounterOfAnswer() {
        if (settings.getShowCountNumberOnButtonMode()) {
            getViewState().showCountOfAnswer(model.getCountOfAnswers());
        } else {
            getViewState().clearCountOfAnswer();
        }
    }
    private ArrayList<Integer> getError(){
        return  settings.getErrorMode() ? model.getErrors() : new ArrayList<>();
    }
}