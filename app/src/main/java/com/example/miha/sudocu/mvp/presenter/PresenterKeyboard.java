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

    public PresenterKeyboard(IRepositorySettings repositorySettings){
        this.settings = repositorySettings;
    }

    public void setModel(Grid model){
        this.model = model;
    }

    private void showCounterOfAnswer() {
        if (settings.getShowCountNumberOnButtonMode()) {
            getViewState().showCountOfAnswer(model.getCountOfAnswers());
        } else {
            getViewState().clearCountOfAnswer();
        }
    }
    public void onResume(){
        showCounterOfAnswer();
    }

    public void deleteAnswer(){
        Integer idAnswer = model.getLastChoiseField();
        Boolean isAnswer = model.isAnswer(idAnswer);
        if (isAnswer) {
            Answer answerForDelete = new Answer("", true, idAnswer);
            getViewState().postEventToClearErrorAndSameAnswerOnUI();
            model.deleteAnswer(answerForDelete);
            getViewState().postEventToUpdateUIAfterDeleteAnswer();
        }
    }
    public void answer(String answer) {
        Integer lastChoseInputId = model.getLastChoiseField();
        if (answer.trim().isEmpty() || lastChoseInputId == null || !model.isAnswer(lastChoseInputId)) {
            return;
        }
        getViewState().postEventToClearErrorAndSameAnswerOnUI();
        model.setAnswer(lastChoseInputId, answer);//установил новый ответ
        getViewState().postEventToShowNewAnswer();
        this.showCounterOfAnswer();
    }

    public void historyForward() {
//        model.incrementHistory();
    }

    public void historyBack() {
//        this.deleteAnswer();
//        history(model.decrementHistory());
    }
}
