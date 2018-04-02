package com.example.miha.sudocu.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.model.Answer;
import com.example.miha.sudocu.mvp.data.model.HistoryAnswer;
import com.example.miha.sudocu.mvp.presenter.interactor.intf.IPresenterGridInteractor;
import com.example.miha.sudocu.mvp.view.intf.IGridView;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;

import java.util.ArrayList;

import rx.Observable;

@InjectViewState
public class PresenterGrid extends MvpPresenter<IGridView> implements IPresenterGrid {
    private Grid model;
    private IPresenterGridInteractor interactor;

    public PresenterGrid(IPresenterGridInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void clearError() {
        Integer id = interactor.getChoiceField();
        getViewState().clearError(interactor.getError());
        interactor.sameAnswerMode(id, null, integers -> getViewState().clearTheSameAnswer(integers));
        interactor.knowAnswerMode(id, null, integers -> getViewState().clearKnownOptions(integers));
    }

    @Override
    public void updateUI() {
        getViewState().showGrid(model.getGameGrid());
        this.showCounterOfAnswer();
        this.disabledHistoryButton();
    }

    @Override
    public void onResume() {
        this.updateUI();
        Integer lastInputId = interactor.getChoiceField();
        if (lastInputId == null) return;

        ArrayList<Integer> errors = interactor.getError();
        showKnowAnswerAndSameAnswer(lastInputId, errors);
        this.showError(errors);
        getViewState().setFocus(lastInputId, errors.contains(lastInputId));
    }

    public void setModel(Grid grid) {
        this.model = grid;
        interactor.setModel(grid);
    }

    public void choseInput(int id) {
        Integer lastInputId = interactor.getChoiceField();
        //проверка на повторный клик по одному и тому же полю
        if (lastInputId != null && lastInputId == id) return;
        ArrayList<Integer> error = interactor.getError();
        if (lastInputId != null) {
            getViewState().removeFocus(lastInputId);
            interactor.knowAnswerMode(lastInputId, error, integers -> getViewState().clearTheSameAnswer(integers));
            interactor.sameAnswerMode(lastInputId, error, integers -> getViewState().clearTheSameAnswer(integers));
        }
        this.showKnowAnswerAndSameAnswer(id, error);
        this.showError(error);
        getViewState().setFocus(id, error.contains(id));
        this.setLastChoise(id);
    }

    public void historyForward() {
        HistoryAnswer historyAnswer = model.incrementHistory();
        Integer id = interactor.getChoiceField();
        if (!id.equals(historyAnswer.getAnswerId())) {
            clearError();
            getViewState().removeFocus(id);
        }
        this.setLastChoise(historyAnswer.getAnswerId());
        answerVisoutAddToHistory(historyAnswer.getAnswer(), historyAnswer.getAnswerId());
        this.disabledHistoryButton();
    }

    public void historyBack() {
        this.clearError();
        Integer id = interactor.getChoiceField();
        Integer historyAnswer = model.getLastFromHistory().getAnswerId();
        if (!id.equals(historyAnswer)) {//если выбрано не поле для ввода
            this.choseInput(historyAnswer);//перешел на последний ответ
            return;
        }

        HistoryAnswer historyDecrementAnswer = model.decrementHistory();
        String newAnswer = id.equals(historyDecrementAnswer.getAnswerId()) ? historyDecrementAnswer.getAnswer() : "";
        this.setAnswer(newAnswer, true, id);
        this.choseInput(historyDecrementAnswer.getAnswerId());//переключил фокус на нужное поле
        id = historyDecrementAnswer.getAnswerId();
        this.showKnowAnswerAndSameAnswer(id, interactor.getError());
        this.showError(interactor.getError());
        getViewState().setFocus(id, interactor.getError().contains(id));
        this.disabledHistoryButton();
    }

    private void setAnswer(String answer, Boolean flag, Integer id) {
        Answer newAnswer = new Answer(answer, flag, id);//delete now
        this.setAnswer(newAnswer.getId(), newAnswer.getNumber());
        getViewState().setTextToAnswer(newAnswer);
    }

    public void answer(String answer) {
        Integer lastChoseInputId = interactor.getChoiceField();
        if (lastChoiseFieldIsNotAnswer(lastChoseInputId, answer)) return;

        addAnswerToHistory(new HistoryAnswer(lastChoseInputId, answer));
        answerVisoutAddToHistory(answer, lastChoseInputId);
    }

    private void addAnswerToHistory(HistoryAnswer answer) {
        model.addAnswerToHistory(answer);
    }

    @Override
    public void deleteAnswer() {
        Integer idAnswer = interactor.getChoiceField();
        String emptyAnswer = "";
        if (lastChoiseFieldIsNotAnswer(idAnswer, emptyAnswer)) return;
        addAnswerToHistory(new HistoryAnswer(idAnswer, emptyAnswer));

        this.clearError();
        Answer answerForDelete = new Answer(emptyAnswer, true, idAnswer);
        setAnswer(idAnswer, emptyAnswer);

        getViewState().setTextToAnswer(answerForDelete);//тут
        getViewState().setFocus(idAnswer, false);
        this.disabledHistoryButton();
        this.showError(interactor.getError());
        showKnowAnswerAndSameAnswer(idAnswer, interactor.getError());
    }

    private Boolean lastChoiseFieldIsNotAnswer(Integer lastChoseInputId, String str) {
        return lastChoseInputId == null || !isAnswer(lastChoseInputId) || getAnswer(lastChoseInputId).equals(str);
    }

    private void showKnowAnswerAndSameAnswer(Integer id, ArrayList<Integer> error) {
        interactor.sameAnswerMode(id, error, integers -> getViewState().showTheSameAnswers(integers));
        interactor.knowAnswerMode(id, error, integers -> getViewState().showKnownOptions(integers));
    }

    private void disabledHistoryButton() {
        getViewState().disableButtonHistoryBack(interactor.isTopOfHistory());
        getViewState().disableButtonHistoryForward(interactor.isBottomOfHistory());
    }

    private void showCounterOfAnswer() {
        if (interactor.getCountOfAnswer() != null) {
            getViewState().showCountOfAnswer(interactor.getCountOfAnswer());
        } else {
            getViewState().clearCountOfAnswer();
        }
    }

    private void setLastChoise(Integer id) {
        model.setLastChoiseField(id);
    }

    private void setAnswer(Integer id, String value) {
        model.setAnswer(id, value);
    }

    private void answerVisoutAddToHistory(String answer, Integer lastChoseInputId) {
        this.clearError();
        this.setAnswer(lastChoseInputId, answer);//установил новый ответ
        this.disabledHistoryButton();

        ArrayList<Integer> error = interactor.getError();
        this.showKnowAnswerAndSameAnswer(lastChoseInputId, error);
        this.showError(error);
        getViewState().setTextToAnswer(new Answer(answer, null, lastChoseInputId));
        getViewState().setFocus(lastChoseInputId, error.contains(lastChoseInputId));
        this.showCounterOfAnswer();
        if (model.isGameOver()) {
            clearError();
            getViewState().removeFocus(interactor.getChoiceField());
            getViewState().gameOver();
        }
    }

    private Boolean isAnswer(Integer i) {
        return model.isAnswer(i);
    }

    private String getAnswer(Integer i) {
        return model.getAnswer(i);
    }

    private void showError(ArrayList<Integer> errors) {
        Observable.from(errors)
                .map(integer -> new Answer(getAnswer(integer), isAnswer(integer),integer))
                .toList()
                .subscribe(answers -> getViewState().showError(answers));
    }
}