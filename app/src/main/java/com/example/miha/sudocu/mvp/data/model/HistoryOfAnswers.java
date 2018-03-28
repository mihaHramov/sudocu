package com.example.miha.sudocu.mvp.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class HistoryOfAnswers implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer history_id;

    @SerializedName("history")
    @Expose
    private ArrayList<HistoryAnswer> history;

    @SerializedName("countOfAnswer")
    @Expose
    private Integer countOfAnswer = 0;

    public HistoryAnswer getLastAnswerFromHistory() {
        return history.get(history_id);
    }

    public HistoryAnswer decrementHistory() {
        if (history_id > 0) {
            history_id--;
        }
        return getLastAnswerFromHistory();
    }

    public Boolean isHead() {
        return (history_id == history.size() - 1) || (history.size() == 0);
    }

    public Boolean isBottom() {
        return (history_id == 0) || (history.size() == 0);
    }

    public Integer getCountOfHistory() {
        return history.size() - 1;
    }

    public HistoryAnswer incrementHistory() {
        Integer lastId = history.size() - 1;
        if (lastId != history_id) {
            history_id++;
        }
        return getLastAnswerFromHistory();
    }

    public void addAnswerToHistory(HistoryAnswer answer) {
        history_id++;
        if (history.size() == 0) {
            history.add(new HistoryAnswer(answer.getAnswerId(), ""));
        }
        history.subList(history_id, history.size()).clear();//добавил нвый ответ в голову  и убрал все после него
        history.add(answer);
        countOfAnswer++;
    }

    public HistoryOfAnswers() {
        history = new ArrayList<>();
        history_id = 0;
    }
    public Integer getCountOfAnswers(){
        return countOfAnswer;
    }
}
