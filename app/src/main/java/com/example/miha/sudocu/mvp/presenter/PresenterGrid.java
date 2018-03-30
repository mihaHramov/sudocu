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


@InjectViewState
public class PresenterGrid extends MvpPresenter<IGridView> implements IPresenterGrid {
    private Grid model;
    private IPresenterGridInteractor interactor;

    public PresenterGrid(IPresenterGridInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void clearError() {
        getViewState().clearError(interactor.getError());
        interactor.sameAnswerMode(model.getLastChoiseField(), new ArrayList<>(), integers -> getViewState().clearTheSameAnswer(integers));
        interactor.knowAnswerMode(model.getLastChoiseField(), new ArrayList<>(), integers -> getViewState().clearKnownOptions(integers));
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
        Integer lastInputId = model.getLastChoiseField();
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
        this.showKnowAnswerAndSameAnswer(id, error);
        this.showError(error);
        getViewState().setFocus(id, error.contains(id));
        model.setLastChoiseField(id);
    }

    public void historyForward() {
        HistoryAnswer historyAnswer = model.incrementHistory();
        if (!model.getLastChoiseField().equals(historyAnswer.getAnswerId())) {
            clearError();
            getViewState().removeFocus(model.getLastChoiseField());
        }
        model.setLastChoiseField(historyAnswer.getAnswerId());
        answerVisoutAddToHistory(historyAnswer.getAnswer(), historyAnswer.getAnswerId());
        this.disabledHistoryButton();
    }

    public void historyBack() {
        if (!model.isAnswer(model.getLastChoiseField())) {//если выбрано не поле для ввода
            HistoryAnswer historyAnswer = model.getLastFromHistory();
            this.clearError();
            this.choseInput(historyAnswer.getAnswerId());//перешел на последний ответ
            return;
        }

        this.clearError();
        HistoryAnswer historyDecrementAnswer = model.decrementHistory();

        if (model.getLastChoiseField().equals(historyDecrementAnswer.getAnswerId())) {
            Answer  newAnswer = new Answer(historyDecrementAnswer.getAnswer(), true, model.getLastChoiseField());
            model.setAnswer(newAnswer.getId(), newAnswer.getNumber());
            getViewState().setTextToAnswer(newAnswer);
        } else {
            Answer newAnswer = new Answer("", true, model.getLastChoiseField());//delete now
            model.setAnswer(newAnswer.getId(), newAnswer.getNumber());
            getViewState().setTextToAnswer(newAnswer);
            this.choseInput(historyDecrementAnswer.getAnswerId());//переключил фокус на нужное поле
            model.incrementHistory();//small fix after decrement history
        }

        this.showKnowAnswerAndSameAnswer(model.getLastChoiseField(), interactor.getError());
        this.showError(interactor.getError());
        getViewState().setFocus(model.getLastChoiseField(), interactor.getError().contains(model.getLastChoiseField()));
        this.disabledHistoryButton();
    }

    public void answer(String answer) {
        Integer lastChoseInputId = model.getLastChoiseField();
        if (lastChoiseFieldIsNotAnswer(lastChoseInputId, answer)) return;
        model.addAnswerToHistory(new HistoryAnswer(lastChoseInputId, answer));
        answerVisoutAddToHistory(answer, lastChoseInputId);
    }

    @Override
    public void deleteAnswer() {
        Integer idAnswer = model.getLastChoiseField();
        String emptyAnswer = "";
        if (lastChoiseFieldIsNotAnswer(idAnswer, emptyAnswer)) return;

        model.addAnswerToHistory(new HistoryAnswer(idAnswer, emptyAnswer));

        this.clearError();
        Answer answerForDelete = new Answer(emptyAnswer, true, idAnswer);
        model.deleteAnswer(answerForDelete);

        getViewState().setTextToAnswer(answerForDelete);//тут
        getViewState().setFocus(model.getLastChoiseField(), false);
        this.disabledHistoryButton();
        showKnowAnswerAndSameAnswer(idAnswer, interactor.getError());
    }

    private Boolean lastChoiseFieldIsNotAnswer(Integer lastChoseInputId, String str) {
        return lastChoseInputId == null || !model.isAnswer(lastChoseInputId) || model.getAnswer(lastChoseInputId).equals(str);
    }

    private void showKnowAnswerAndSameAnswer(Integer id, ArrayList<Integer> error) {
        interactor.sameAnswerMode(id, error, integers -> getViewState().showTheSameAnswers(integers));
        interactor.knowAnswerMode(id, error, integers -> getViewState().showKnownOptions(integers));
    }

    private void disabledHistoryButton() {
        getViewState().disableButtonHistoryBack(!model.isFirstAnswerOfHistory());
        getViewState().disableButtonHistoryForward(!model.isLastAnswerOfHistory());
    }

    private void showCounterOfAnswer() {
//        settings.getShowCountNumberOnButtonMode()
        if (true) {
            getViewState().showCountOfAnswer(model.getCountOfAnswers());
        } else {
            getViewState().clearCountOfAnswer();
        }
    }

    private void answerVisoutAddToHistory(String answer, Integer lastChoseInputId) {
        this.clearError();
        model.setAnswer(lastChoseInputId, answer);//установил новый ответ
        this.disabledHistoryButton();

        ArrayList<Integer> error = interactor.getError();
        this.showKnowAnswerAndSameAnswer(lastChoseInputId, error);
        this.showError(error);
        getViewState().setTextToAnswer(new Answer(answer, null, lastChoseInputId));
        getViewState().setFocus(lastChoseInputId, error.contains(lastChoseInputId));
        this.showCounterOfAnswer();
        if(model.isGameOver()){
            clearError();
            getViewState().removeFocus(model.getLastChoiseField());
            getViewState().gameOver();
        }
    }

    private void showError(ArrayList<Integer> errors) {
        ArrayList<Answer> errorsToUi = new ArrayList<>();
        for (Integer error : errors) {
            Answer answer = new Answer(model.getAnswer(error), model.isAnswer(error));
            answer.setId(error);
            errorsToUi.add(answer);
        }
        getViewState().showError(errorsToUi);
    }
}