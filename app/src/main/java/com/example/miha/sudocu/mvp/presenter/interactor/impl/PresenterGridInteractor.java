package com.example.miha.sudocu.mvp.presenter.interactor.impl;


import com.example.miha.sudocu.mvp.data.DP.intf.IRepositorySettings;
import com.example.miha.sudocu.mvp.data.model.Answer;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.data.model.HistoryAnswer;
import com.example.miha.sudocu.mvp.presenter.interactor.intf.IPresenterGridInteractor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class PresenterGridInteractor implements IPresenterGridInteractor {
    private IRepositorySettings settings;
    private Grid model;
    private String emptyString = "";

    @Override
    public Boolean isChoiceFieldNotSameAnswer(String str) {
        Integer id = model.getLastChoiseField();
        return !(id == null || str.equals(model.getAnswer(id)));
    }

    @Override
    public HistoryAnswer getCurrentHistoryAnswer() {
        return model.getLastFromHistory();
    }

    @Override
    public Boolean isChoiceFieldNotKnowByDefault(Integer id) {
        return id != null && model.isAnswer(id);
    }

    @Override
    public Boolean isGameOver() {
        return model.isGameOver();
    }

    @Override
    public void deleteChoiceAnswer(Integer id) {
        if(!model.getAnswer(id).equals(emptyString)) {
            model.setAnswer(id, emptyString);
            model.addAnswerToHistory(new HistoryAnswer(id, emptyString, getAnswers()));
        }
    }

    private Map<Integer, String> getAnswers() {
        Map<Integer, String> result = new Hashtable<>();
        for (Map.Entry<Integer, String> temp : model.getAnswers().entrySet()) {
            result.put(temp.getKey(), model.getAnswer(temp.getKey()));
        }
        return result;
    }

    @Override
    public void setChoiceField(Integer id) {
        model.setLastChoiseField(id);
    }

    @Override
    public List<Integer> sameAnswerMode(Integer id, ArrayList<Integer> error) {
        ArrayList<Integer> sameAnswer = settings.getShowSameAnswerMode() && !model.getAnswer(id).trim().isEmpty() ? model.getTheSameAnswers(id) : new ArrayList<>();
        sameAnswer.removeAll(error != null ? error : new ArrayList<Integer>());
        return sameAnswer;
    }

    @Override
    public HistoryAnswer historyPrev() {
        HistoryAnswer answer = model.decrementHistory();
        for (Map.Entry<Integer, String> temp : answer.getAnswers().entrySet()) {
            model.setAnswer(temp.getKey(), temp.getValue());
        }
        model.setLastChoiseField(answer.getAnswerId());
        return answer;
    }

    @Override
    public HistoryAnswer historyNext() {
        HistoryAnswer answer = model.incrementHistory();
        model.setLastChoiseField(answer.getAnswerId());
        for (Map.Entry<Integer, String> temp : answer.getAnswers().entrySet()) {
            model.setAnswer(temp.getKey(), temp.getValue());
        }
        return answer;
    }

    @Override
    public Answer[][] getGameField() {
        return model.getGameGrid();
    }

    @Override
    public void addNewAnswer(Answer answer) {
        model.setAnswer(answer.getId(),answer.getNumber());
        model.addAnswerToHistory(new HistoryAnswer(answer.getId(), answer.getNumber(), getAnswers()));
    }

    @Override
    public List<Integer> knowAnswerMode(Integer id, ArrayList<Integer> error) {
        ArrayList<Integer> knowOptions = settings.getKnowAnswerMode() ? model.getKnowOptions(id) : new ArrayList<>();
        knowOptions.removeAll(error != null ? error : new ArrayList<Integer>());
        return knowOptions;
    }

    public PresenterGridInteractor(IRepositorySettings sett) {
        this.settings = sett;
    }

    @Override
    public void setModel(Grid grid) {
        this.model = grid;
    }

    @Override
    public ArrayList<Integer> getError() {
        return settings.getErrorMode() ? model.getErrors() : new ArrayList<>();
    }

    @Override
    public Map<String, String> getCountOfAnswer() {
        Map<String, String> result = new Hashtable<>();
        for (Map.Entry<String, Integer> temp : model.getCountOfAnswers().entrySet()) {
            String strResult = settings.getShowCountNumberOnButtonMode() ? temp.getValue().toString() : emptyString;
            result.put(temp.getKey(), strResult);
        }
        return result;
    }

    @Override
    public Integer getChoiceField() {
        return model.getLastChoiseField();
    }

    @Override
    public Boolean isTopOfHistory() {
        return !model.isFirstAnswerOfHistory();
    }

    @Override
    public Boolean isBottomOfHistory() {
        return !model.isLastAnswerOfHistory();
    }
}
