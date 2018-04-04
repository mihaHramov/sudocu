package com.example.miha.sudocu.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.Pair;
import com.example.miha.sudocu.mvp.data.model.Answer;
import com.example.miha.sudocu.mvp.data.model.HistoryAnswer;
import com.example.miha.sudocu.mvp.presenter.interactor.intf.IPresenterGridInteractor;
import com.example.miha.sudocu.mvp.view.constant.BackgroundOfField;
import com.example.miha.sudocu.mvp.view.intf.IGridView;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.presenter.IPresenter.IPresenterGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;

@InjectViewState
public class PresenterGrid extends MvpPresenter<IGridView> implements IPresenterGrid {
    private IPresenterGridInteractor interactor;

    public PresenterGrid(IPresenterGridInteractor interactor) {
        this.interactor = interactor;
    }

    private void clearError(Integer id) {
        Observable.just(id)
                .flatMap(integer -> Observable.merge(
                        Observable.just(interactor.getError()),
                        Observable.just(interactor.sameAnswerMode(integer, null)),
                        Observable.just(interactor.knowAnswerMode(integer, null))))
                .subscribe(integers -> getViewState().clearField(integers));
    }

    @Override
    public void updateUI() {
        getViewState().showGrid(interactor.getGameField());
        getViewState().showCountOfAnswer(interactor.getCountOfAnswer());
        getViewState().disableButtonHistoryButton(interactor.isTopOfHistory(), interactor.isBottomOfHistory());
    }

    @Override
    public void onResume() {
        this.updateUI();
        Integer id = interactor.getChoiceField();
        if (id == null) return;

        ArrayList<Integer> error = interactor.getError();
        this.showKnowAnswerAndSameAnswerAndError(id, error);
        this.setFocus(id, error);
    }

    public void setModel(Grid grid) {
        interactor.setModel(grid);
    }

    @Override
    public void choseInput(Integer id) {
        Integer lastInputId = interactor.getChoiceField();
        if (id.equals(lastInputId)) return;

        ArrayList<Integer> error = interactor.getError();
        if (lastInputId != null) {
            getViewState().removeFocus(lastInputId);
            getViewState().clearField(interactor.sameAnswerMode(lastInputId, error));
            getViewState().clearField(interactor.knowAnswerMode(lastInputId, error));
        }
        this.showKnowAnswerAndSameAnswerAndError(id, error);
        this.setFocus(id, error);
        interactor.setChoiceField(id);
    }

    public void historyForward() {
        Integer id = interactor.getChoiceField();
        this.clearError(id);

        HistoryAnswer historyAnswer = interactor.historyNext();
        if (!id.equals(historyAnswer.getAnswerId())) {
            getViewState().removeFocus(id);
        }

        this.changeUiAfterAnswerChange(historyAnswer.getAnswer(), historyAnswer.getAnswerId());
        getViewState().disableButtonHistoryButton(interactor.isTopOfHistory(), interactor.isBottomOfHistory());
    }

    public void historyBack() {
        this.clearError(interactor.getChoiceField());
        Integer currentHistoryAnswerID = interactor.getCurrentHistoryAnswer().getAnswerId();
        if (!currentHistoryAnswerID.equals(interactor.getChoiceField())) {
            this.choseInput(currentHistoryAnswerID);
            return;
        }

        for (Map.Entry<Integer, String> val : interactor.historyPrev().getAnswers().entrySet()) {
            getViewState().setTextToAnswer(new Answer(val.getValue(), true, val.getKey()));
        }

        Integer id = interactor.getChoiceField();
        ArrayList<Integer> err = interactor.getError();
        Boolean isContainsError = err.contains(id);

        this.showKnowAnswerAndSameAnswerAndError(id, err);
        Integer result;
        if (isContainsError) {
            result = interactor.isChoiceFieldNotKnowByDefault(id) ? BackgroundOfField.ErrorAnswerFocus : BackgroundOfField.ErrorKnowAnswerFocus;
        } else {
            result = BackgroundOfField.Focus;
        }
        getViewState().setFocus(new Pair<>(id, result));
        getViewState().disableButtonHistoryButton(interactor.isTopOfHistory(), interactor.isBottomOfHistory());
    }

    public void answer(String answer) {
        Integer lastChoseInputId = interactor.getChoiceField();
        if ((!interactor.isChoiceFieldNotKnowByDefault(lastChoseInputId) && interactor.isChoiceFieldNotSameAnswer(answer)) || lastChoseInputId == null)
            return;
        this.clearError(lastChoseInputId);
        interactor.addNewAnswer(new Answer(answer, true, lastChoseInputId));
        this.changeUiAfterAnswerChange(answer, lastChoseInputId);
        getViewState().disableButtonHistoryButton(interactor.isTopOfHistory(), interactor.isBottomOfHistory());
        if (interactor.isGameOver()) {
            this.clearError(lastChoseInputId);
            getViewState().removeFocus(interactor.getChoiceField());
            getViewState().gameOver();
        }
    }

    @Override
    public void deleteAnswer() {
        Integer id = interactor.getChoiceField();
        if (!interactor.isChoiceFieldNotKnowByDefault(id)) return;
        clearError(id);
        interactor.deleteChoiceAnswer(id);
        getViewState().setTextToAnswer(new Answer("", true, id));
        this.setFocus(id, null);
        ArrayList<Integer> err = interactor.getError();
        this.showKnowAnswerAndSameAnswerAndError(id, err);
        getViewState().disableButtonHistoryButton(interactor.isTopOfHistory(), interactor.isBottomOfHistory());
    }

    private void showKnowAnswerAndSameAnswerAndError(Integer id, ArrayList<Integer> error) {
        Observable.just(error)
                .flatMap(list -> getObserverToColorThePlayingField(interactor.sameAnswerMode(id, list), BackgroundOfField.SameAnswer).mergeWith(getObserverToColorThePlayingField(interactor.knowAnswerMode(id, list), BackgroundOfField.KnowAnswer)))
                .toList()
                .subscribe(pairs -> getViewState().colorThePlayingField(pairs));

        Observable.just(error)
                .flatMap(Observable::from)
                .map(integer -> new Answer("", interactor.isChoiceFieldNotKnowByDefault(integer), integer))
                .toList()
                .subscribe(answers -> getViewState().showError(answers));
    }

    private Observable<Pair<Integer, Integer>> getObserverToColorThePlayingField(List<Integer> list, final Integer ofFieldEnum) {
        return Observable.from(list).map(integer -> new Pair<>(integer, ofFieldEnum));
    }

    private void changeUiAfterAnswerChange(String answer, Integer id) {
        ArrayList<Integer> error = interactor.getError();
        getViewState().setTextToAnswer(new Answer(answer, null, id));
        this.showKnowAnswerAndSameAnswerAndError(id, error);
        this.setFocus(id, error);
        getViewState().showCountOfAnswer(interactor.getCountOfAnswer());
    }

    private void setFocus(Integer id, ArrayList<Integer> error) {
        Integer result = BackgroundOfField.Focus;
        if (error != null && error.contains(id)) {
            result = interactor.isChoiceFieldNotKnowByDefault(id) ? BackgroundOfField.ErrorAnswerFocus : BackgroundOfField.ErrorKnowAnswerFocus;
        }
        getViewState().setFocus(new Pair<>(id, result));
    }
}