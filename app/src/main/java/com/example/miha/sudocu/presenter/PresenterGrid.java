package com.example.miha.sudocu.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.miha.sudocu.data.DP.GenerateGame;
import com.example.miha.sudocu.data.DP.MementoMainActivity;
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
    private Grid model;
    private Intent intent;
    private MementoMainActivity activityInfo;

    public PresenterGrid(IRepository repository) {
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

    private void initModel() {
        int complex = intent.getIntExtra(AlertDialog.SETTINGS, 1);
        model = new Grid().setComplexity(complex).setUndefined(complex).init(new GenerateGame());
    }

    public void init(Intent intent) {
        saveData = false;
        this.intent = intent;
        if (intent.getSerializableExtra(Grid.KEY) != null) {
            model = (Grid) intent.getSerializableExtra(Grid.KEY);
        }
        if (model == null) {//если новая игра
            initModel();
        }
        viewState.showGameGrid();
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
                    }, throwable -> Log.d("mihaHramov", throwable.getMessage()), () -> Log.d("mihaHramov", "saveComplete"));
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

        activityInfo.addError(lastChoseInputId, model.getErrors(lastChoseInputId));//выбрал ошибки для текущего
        activityInfo.setLastAnswer(answer);

        viewState.showErrorInput();

        ArrayList<Integer> knowOption = model.getKnowOptions(lastChoseInputId);
        knowOption.removeAll(activityInfo.getError());
        activityInfo.setKnowOption(knowOption);

        viewState.showAnswer();
        viewState.showKnownOptions();
        ArrayList<Integer> sameAnswers = model.getTheSameAnswers(activityInfo.getLastChoseInputId());
        sameAnswers.removeAll(activityInfo.getError());
        activityInfo.setSameAnswer(sameAnswers);
        viewState.showTheSameAnswers();

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

        viewState.showKnownOptions();//показать известные варианты
        if (error.contains(activityInfo.getLastChoseInputId())) {
            viewState.showErrorFocus();
        } else {
            viewState.focus();
        }
    }

    //нужен для отслеживания состояния активити
    public class ViewGridState {
        private ArrayList<Integer> myList = new ArrayList<>();
        private IGridView view;
        private final int SHOW_GRID = 0;
        private final int SHOW_KNOW_OPTIONS = 1;
        private final int FOCUS_INPUT = 3;
        private final int REMOVE_FOCUS_INPUT = 2;

        private static final int SHOW_GAME_OVER = 4;
        private static final int SHOW_SAME_ANSWER = 5;
        private static final int SHOW_ERROR_FOCUS = 6;
        private static final int SHOW_ERRORS = 7;
        private static final int CLEAR_ERRORS = 8;
        private static final int CLEAR_KNOW_OPTION = 9;
        private static final int CLEAR_KNOW_ANSWER = 10;
        private static final int SHOW_ANSWER = 11;


        private  void attachView(IGridView view) {
            this.view = view;
            for (Integer i : myList) {
                switch (i) {
                    case SHOW_GRID:
                        view.showGrid(model.getGameGrid());
                        Log.d("miahHramov", "gameGrid");
                        break;
//                    case REMOVE_FOCUS_INPUT:
//                        view.removeFocus(activityInfo.getLastChoseInputId());
//                        Log.d("miahHramov", "removeFocus");
//                        break;
//                    case FOCUS_INPUT:
//                        view.setFocus(activityInfo.getLastChoseInputId());
//                        Log.d("miahHramov", "focus");
//                        break;
//                    case SHOW_KNOW_OPTIONS:
//                        view.showKnownOptions(activityInfo.getKnowOption());
//                        break;
                }
            }
        }


        private boolean isViewAttach() {
            return view != null;
        }


        private void removeFocus() {
            if (activityInfo.getLastChoseInputId() != null) {
                if (isViewAttach()) {
                    view.removeFocus(activityInfo.getLastChoseInputId());
                }
                addToList(REMOVE_FOCUS_INPUT);
            }
        }

        public void showAnswer() {
            if (isViewAttach()) {
                view.setTextToAnswer(activityInfo.getLastChoseInputId(), activityInfo.getLastAnswer());
            }
            addToList(SHOW_ANSWER);
        }

        public void showKnownOptions() {
            if (isViewAttach()) {
                view.showKnownOptions(activityInfo.getKnowOption());
            }
            addToList(SHOW_KNOW_OPTIONS);
        }

        public void showErrorInput() {
            if (activityInfo.getLastAnswer() != null && activityInfo.getLastChoseInputId() != null) {
                if (!model.getAnswer(activityInfo.getLastChoseInputId()).isEmpty()) {
                    if (isViewAttach()) {
                        view.showError(activityInfo.getError());
                    }
                    addToList(SHOW_ERRORS);
                }
            }
        }

        public void clearHistory() {
            activityInfo.clear();
            myList.clear();
        }

        public void showTheSameAnswers() {
            if (isViewAttach()) {
                view.showTheSameAnswers(activityInfo.getSameAnswer());
            }
            addToList(SHOW_SAME_ANSWER);
        }

        public void focus() {
            if (isViewAttach()) {
                view.setFocus(activityInfo.getLastChoseInputId());
            }
            addToList(FOCUS_INPUT);
        }

        public void showGameOver() {
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

        public void addToList(int k) {
            myList.add(k);
           /* if (myList.contains(k)) {
                myList.set(myList.indexOf(k), k);
            } else {
                myList.add(k);
            }*/
        }


        public void detachView() {
            this.view = null;
        }

        public void clearTheSameAnswer() {
            if (activityInfo.getLastChoseInputId() != null) {
                if (isViewAttach()) {
                    view.clearTheSameAnswer(activityInfo.getSameAnswer());
                }
                addToList(CLEAR_KNOW_ANSWER);//возможна ошибка
            }

        }

        public void clearKnownOptions() {
            if (activityInfo.getLastChoseInputId() == null) return;
            if (isViewAttach()) {
                view.clearKnownOptions(activityInfo.getKnowOption());
            }
            addToList(CLEAR_KNOW_OPTION);
        }

        public void clearError() {
            if (isViewAttach()) {
                view.clearError(activityInfo.getErrorForClean());
            }
            addToList(CLEAR_ERRORS);
        }

        public void showErrorFocus() {
            if (isViewAttach()) {
                view.showErrorFocus(activityInfo.getLastChoseInputId());
            }
            addToList(SHOW_ERROR_FOCUS);
        }
    }
}