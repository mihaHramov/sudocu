package com.example.miha.sudocu.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.model.Answer;
import com.example.miha.sudocu.mvp.presenter.interactor.intf.IPresenterGridInteractor;
import com.example.miha.sudocu.mvp.view.intf.IGridView;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;

import java.util.ArrayList;


@InjectViewState
public class PresenterGrid extends MvpPresenter<IGridView> implements IPresenterGrid {
    private Grid model;
    private IPresenterGridInteractor interactor;

    @Override
    public void clearError() {
        getViewState().clearError(interactor.getError());
        interactor.sameAnswerMode(model.getLastChoiseField(), new ArrayList<>(), integers -> getViewState().clearTheSameAnswer(integers));
    }

    @Override
    public void updateUI() {
        getViewState().showGrid(model.getGameGrid());
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {
        this.updateUI();
        Integer lastInputId = model.getLastChoiseField();
        if (lastInputId == null) return;

        ArrayList<Integer> errors = interactor.getError();
        showKnowAnswerAndSameAnswer(lastInputId, errors);
        getViewState().showError(errors);
        getViewState().setFocus(lastInputId, errors.contains(lastInputId));
    }

    public PresenterGrid(IPresenterGridInteractor interactor) {
        this.interactor = interactor;
    }

    public void setModel(Grid grid) {
        this.model = grid;
        interactor.setModel(grid);
    }

    public void answer() {
        String answer = model.getAnswer(model.getLastChoiseField());
        Integer lastChoseInputId = model.getLastChoiseField();
        ArrayList<Integer> error = interactor.getError();
        this.showKnowAnswerAndSameAnswer(lastChoseInputId, error);
        getViewState().showError(error);
        getViewState().setTextToAnswer(new Answer(answer, null, lastChoseInputId));
        getViewState().setFocus(lastChoseInputId, error.contains(lastChoseInputId));
    }

    @Override
    public void deleteAnswer() {
        Answer answerForDelete = new Answer("", true, model.getLastChoiseField());
        interactor.knowAnswerMode(model.getLastChoiseField(), new ArrayList<>(), integers -> getViewState().showKnownOptions(integers));
        getViewState().setTextToAnswer(answerForDelete);
        getViewState().setFocus(model.getLastChoiseField(), false);
        getViewState().showError(interactor.getError());
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
        showKnowAnswerAndSameAnswer(id, error);
        getViewState().showError(error);
        getViewState().setFocus(id, error.contains(id));
        model.setLastChoiseField(id);
    }

    private void showKnowAnswerAndSameAnswer(Integer id, ArrayList<Integer> error) {
        interactor.sameAnswerMode(id, error, integers -> getViewState().showTheSameAnswers(integers));
        interactor.knowAnswerMode(id, error, integers -> getViewState().showKnownOptions(integers));
    }
}