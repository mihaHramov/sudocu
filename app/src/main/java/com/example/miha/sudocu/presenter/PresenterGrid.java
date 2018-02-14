package com.example.miha.sudocu.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.data.model.Answer;
import com.example.miha.sudocu.data.model.HistoryAnswer;
import com.example.miha.sudocu.view.intf.IGridView;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.data.DP.intf.IRepository;
import com.example.miha.sudocu.presenter.IPresenter.IPresenterGrid;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class PresenterGrid extends MvpPresenter<IGridView> implements IPresenterGrid {
    private Subscription subscription;
    private IRepository repository;
    private IRepositorySettings settings;
    private Grid model;
    private Scheduler scheduler;

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
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {}, throwable -> Log.d("mihaHramov", throwable.getMessage()), () -> {});
        subscription.unsubscribe();
    }

    @Override
    public void onResume() {
        Boolean modeError = !settings.getErrorMode();
        ArrayList<Integer> knowOpt = new ArrayList<>();
        ArrayList<Integer> sameAnswers = new ArrayList<>();
        ArrayList<Integer> errors = new ArrayList<>();
        Integer lastInputId = model.getLastChoiseField();

        if (settings.getKnowAnswerMode()) {
            knowOpt = model.getKnowOptions(lastInputId);
        }

        if (settings.getShowSameAnswerMode()) {
            if (lastInputId != null && !isEmptyString(model.getAnswer(lastInputId))) {
                sameAnswers = model.getTheSameAnswers(lastInputId);
            }
        }

        if (!modeError) {
            errors = model.getErrors();
            knowOpt.removeAll(errors);
            sameAnswers.removeAll(errors);
        }

        getViewState().showGrid(model.getGameGrid());
        getViewState().clearError(errors);//возможно убрать
        getViewState().showError(errors);
        getViewState().showTheSameAnswers(sameAnswers);
        getViewState().showKnownOptions(knowOpt);
        getViewState().setGameName(model.getName());

        if (settings.getShowCountNumberOnButtonMode()) {
            getViewState().showCountOfAnswer(model.getCountOfAnswers());
        } else {
            getViewState().clearCountOfAnswer();
        }

        if (lastInputId != null) {
            getViewState().setFocus(lastInputId, errors.contains(lastInputId));
        }
        if (subscription == null || subscription.isUnsubscribed()) {
            subscription = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread()).subscribe(aLong -> {
                        getViewState().setGameTime(model.getGameTime());
                        model.setGameTime(model.getGameTime() + 1);
                    });
        }
    }

    public PresenterGrid(IRepository repository, IRepositorySettings repositorySettings) {
        this.settings = repositorySettings;
        this.repository = repository;
    }
    public PresenterGrid setModel(Grid grid){
        this.model = grid;
        return this;
    }

    @Override
    public void reloadGame() {
        model.reloadGame().getGameGrid();
        getViewState().showGrid(model.getGameGrid());
    }

    private void addAnswer(String answer, Integer lastChoseInputId) {

        ArrayList<Integer> clearAnswer = model.getTheSameAnswers(lastChoseInputId);
        getViewState().clearTheSameAnswer(clearAnswer);
        getViewState().clearError(model.getErrors());
        model.setAnswer(lastChoseInputId, answer);//установил новый ответ
        ArrayList<Integer> error = new ArrayList<>();
        if (settings.getErrorMode()) {
            error = model.getErrors();
            getViewState().showError(error);
        }
        getViewState().setTextToAnswer(new Answer( answer,null,lastChoseInputId));

        if (settings.getKnowAnswerMode()) {
            ArrayList<Integer> knowOption = model.getKnowOptions(lastChoseInputId);
            knowOption.removeAll(error);
            getViewState().showKnownOptions(knowOption);
        }
        if (settings.getShowSameAnswerMode()) {
            ArrayList<Integer> sameAnswers = model.getTheSameAnswers(lastChoseInputId);
            sameAnswers.removeAll(error);
            getViewState().showTheSameAnswers(sameAnswers);
        }

        if (settings.getShowCountNumberOnButtonMode()) {
            Map<String, Integer> count = model.getCountOfAnswers();
            getViewState().showCountOfAnswer(count);
        }
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
        if (isAnswer) {
            Answer answerForDelete = new Answer("", isAnswer,idAnswer);
            getViewState().clearError(model.getErrors());
            getViewState().clearTheSameAnswer(model.getTheSameAnswers(idAnswer));
            model.deleteAnswer(answerForDelete);
            getViewState().setTextToAnswer(answerForDelete);
            getViewState().showKnownOptions(model.getKnowOptions(idAnswer));
            getViewState().setFocus(idAnswer, false);
            getViewState().showError(model.getErrors());
        }
    }

    public void choseInput(int id) {
        Integer lastInputId = model.getLastChoiseField();
        //проверка на повторный клик по одному и тому же полю
        if (lastInputId != null && lastInputId == id) {
            return;
        }
        ArrayList<Integer> error = new ArrayList<>();
        if (settings.getErrorMode()) {
            error = model.getErrors();
        }
        if (lastInputId != null) {
            ArrayList<Integer> clearKnowOption = model.getKnowOptions(lastInputId);
            clearKnowOption.removeAll(error);
            getViewState().clearTheSameAnswer(clearKnowOption);
            getViewState().removeFocus(lastInputId);

            ArrayList<Integer> clearSameAnswer = model.getTheSameAnswers(lastInputId);
            clearSameAnswer.removeAll(error);
            getViewState().clearTheSameAnswer(clearSameAnswer);
        }

        getViewState().showError(error);

        if (!isEmptyString(model.getAnswer(id))) {
            if (settings.getShowSameAnswerMode()) {
                ArrayList<Integer> sameAnswer = model.getTheSameAnswers(id);
                sameAnswer.removeAll(error);
                getViewState().showTheSameAnswers(sameAnswer);
            }
        }

        if (settings.getKnowAnswerMode()) {
            ArrayList<Integer> knowOptions = model.getKnowOptions(id);
            knowOptions.removeAll(error);
            getViewState().showKnownOptions(knowOptions);
        }
        getViewState().setFocus(id, error.contains(id));
        model.setLastChoiseField(id);
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}