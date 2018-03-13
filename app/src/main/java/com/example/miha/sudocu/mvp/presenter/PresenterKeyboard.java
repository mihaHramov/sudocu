package com.example.miha.sudocu.mvp.presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.data.model.Answer;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.data.model.HistoryAnswer;
import com.example.miha.sudocu.mvp.view.intf.IKeyboard;


@InjectViewState
public class PresenterKeyboard extends MvpPresenter<IKeyboard> {
    private IRepositorySettings settings;
    private Grid model;

    public PresenterKeyboard(IRepositorySettings repositorySettings) {
        this.settings = repositorySettings;
    }

    public void setModel(Grid model) {
        this.model = model;
    }

    private void showCounterOfAnswer() {
        if (settings.getShowCountNumberOnButtonMode()) {
            getViewState().showCountOfAnswer(model.getCountOfAnswers());
        } else {
            getViewState().clearCountOfAnswer();
        }
    }

    private void disabledHistoryButton() {
        getViewState().disableButtonHistoryBack(!model.isFirstAnswerOfHistory());
        getViewState().disableButtonHistoryForward(!model.isLastAnswerOfHistory());
    }

    public void onResume() {
        this.showCounterOfAnswer();
        this.disabledHistoryButton();
    }

    public void deleteAnswer() {
        Integer idAnswer = model.getLastChoiseField();
        String emptyAnswer = "";
        if (!lastChoiseFieldIsNotAnswer(idAnswer,emptyAnswer)) {
            model.addAnswerToHistory(new HistoryAnswer(idAnswer, emptyAnswer));
            this.deleteAnswerVisoutAddToHistory(emptyAnswer, idAnswer);
        }
    }

    private Boolean lastChoiseFieldIsNotAnswer(Integer lastChoseInputId,String str) {
        return lastChoseInputId == null || !model.isAnswer(lastChoseInputId)||model.getAnswer(lastChoseInputId).equals(str);
    }

    public void answer(String answer) {
        Integer lastChoseInputId = model.getLastChoiseField();
        if (!lastChoiseFieldIsNotAnswer(lastChoseInputId,answer)){
            model.addAnswerToHistory(new HistoryAnswer(lastChoseInputId, answer));
            answerVisoutAddToHistory(answer, lastChoseInputId);
        }
    }

    private void deleteAnswerVisoutAddToHistory(String emptyAnswer, Integer idAnswer) {
        Answer answerForDelete = new Answer(emptyAnswer, true, idAnswer);
        getViewState().postEventToClearErrorAndSameAnswerOnUI();
        model.deleteAnswer(answerForDelete);
        getViewState().postEventToUpdateUIAfterDeleteAnswer();
        this.disabledHistoryButton();
    }

    private void answerVisoutAddToHistory(String answer, Integer lastChoseInputId) {
        getViewState().postEventToClearErrorAndSameAnswerOnUI();
        model.setAnswer(lastChoseInputId, answer);//установил новый ответ
        this.disabledHistoryButton();
        getViewState().postEventToShowNewAnswer();
        this.showCounterOfAnswer();
    }

    private void historyUtil(HistoryAnswer historyAnswer) {
        this.disabledHistoryButton();
        model.setLastChoiseField(historyAnswer.getAnswerId());
        answerVisoutAddToHistory(historyAnswer.getAnswer(), historyAnswer.getAnswerId());
    }

    public void historyForward() {
        this.historyUtil(model.incrementHistory());
    }

    public void historyBack() {
        this.deleteAnswerVisoutAddToHistory("", model.getLastChoiseField());
        historyUtil(model.decrementHistory());
    }
}