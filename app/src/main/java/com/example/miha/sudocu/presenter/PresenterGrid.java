package com.example.miha.sudocu.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.miha.sudocu.data.DP.GenerateGame;
import com.example.miha.sudocu.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.data.model.Answer;
import com.example.miha.sudocu.data.model.HistoryAnswer;
import com.example.miha.sudocu.presenter.Adapter.AlertDialog;
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

public class PresenterGrid implements IPresenterGrid {
    private Subscription subscription;
    private ViewGridState viewState;
    private boolean saveData;
    private IRepository repository;
    private IRepositorySettings settings;
    private Grid model;
    private Scheduler scheduler;

    @Override
    public void replayGame() {
        viewState.clearError();
        model.replayGame();
        viewState.showGameGrid();
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
                .subscribe(aVoid -> {
                }, throwable -> Log.d("mihaHramov", throwable.getMessage()), () -> {
                });
        subscription.unsubscribe();
    }

    @Override
    public void onResume() {
        if (subscription == null || subscription.isUnsubscribed()) {
            subscription = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread()).subscribe(aLong -> {
                        viewState.showGameTime(model.getGameTime());
                        model.setGameTime(model.getGameTime() + 1);
                    });
        }
    }

    public PresenterGrid(IRepository repository, IRepositorySettings repositorySettings) {
        this.settings = repositorySettings;
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
        if (model.getLastChoiseField() != null) {
            viewState.setLastId(model.getLastChoiseField());
        }
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
            if (viewState.getLastId() != null && !model.getAnswer(viewState.getLastId()).trim().isEmpty()) {
                sameAnswers = model.getTheSameAnswers(viewState.getLastId());
            }

        }
        if (!modeError) {
            sameAnswers.removeAll(errors);
        }
        viewState.setSameAnswer(sameAnswers);
        viewState.attachView(view);
        viewState.showGameName(model.getName());
        if (settings.getShowCountNumberOnButtonMode()) {
            viewState.showCountAnswer(model.getCountOfAnswers());
        } else {
            viewState.clearCountAnswer();
        }
    }
    public void onSaveInstanceState(Bundle outState) {
        saveData = true;
        viewState.detachView();
    }

    @Override
    public void unSubscription() {
        if (!saveData) {
            model = null;
            viewState.clearHistory();
        }
    }

    private void addAnswer(String answer, Integer lastChoseInputId) {

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

        if (settings.getShowCountNumberOnButtonMode()) {
            Map<String, Integer> count = model.getCountOfAnswers();
            viewState.showCountAnswer(count);
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

    public void answer(String answer) {
        Integer lastChoseInputId = model.getLastChoiseField();
        if (answer.trim().isEmpty() || lastChoseInputId == null || !model.isAnswer(lastChoseInputId)) {
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
        Answer answerForDelete = new Answer("", isAnswer);
        answerForDelete.setId(idAnswer);
        if (isAnswer) {
            viewState.clearError();
            viewState.clearTheSameAnswer(model.getTheSameAnswers(idAnswer));
            model.deleteAnswer(answerForDelete);
            viewState.showAnswer(idAnswer, answerForDelete.getNumber());
            viewState.showKnownOptions(model.getKnowOptions(answerForDelete.getId()));
            viewState.focus(answerForDelete.getId());
            viewState.showErrorInput(model.getErrors());
        }
    }

    public void choseInput(int id) {
        Integer lastInputId = model.getLastChoiseField();
        if (lastInputId != null && lastInputId == id) {//проверка на повторный клик по одному и тому же полю
            return;
        }
        ArrayList<Integer> error = new ArrayList<>();
        if (settings.getErrorMode()) {
            error = model.getErrors();//получил ошибки
        }
        if (lastInputId != null) {
            ArrayList<Integer> clearKnowOption = model.getKnowOptions(lastInputId);
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
            if (settings.getShowSameAnswerMode()) {
                viewState.showTheSameAnswers(sameAnswer);
            }
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
        model.setLastChoiseField(id);
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    //нужен для отслеживания состояния активити
    private class ViewGridState {
        private IGridView view;
        private Integer lastId;
        private ArrayList<Integer> sameAnswer;
        private ArrayList<Integer> knowOption;
        private ArrayList<Integer> errors;
        private Long gameTime;
        private Map<String, Integer> countAnswer;

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

        public void showGameTime(Long l) {
            gameTime = l;
            if (isViewAttach() && gameTime != null) {
                view.setGameTime(gameTime);
            }
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
            showGameTime(gameTime);
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

        public void showGameName(String name) {
            view.setGameName(name);
        }

        public void showCountAnswer(Map<String, Integer> count) {
            countAnswer = count;
            if (isViewAttach()) {
                view.showCountOfAnswer(countAnswer);
            }
        }

        public void clearCountAnswer() {
            if (isViewAttach()) {
                view.clearCountOfAnswer();
            }
        }
    }//end viewState
}