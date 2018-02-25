package com.example.miha.sudocu.mvp.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class HistoryOfAnswers implements Serializable {
    @SerializedName("isTopStack")
    @Expose
    private Boolean isTopStack;

    @SerializedName("id")
    @Expose
    private Integer history_id;

    @SerializedName("history")
    @Expose
    private ArrayList<HistoryAnswer> history;

    private HistoryAnswer getLastAnswerFromHistory() {
        return history.get(history_id);
    }

    public HistoryAnswer decrementHistory() {
        if (history_id == 0) {
            isTopStack = true;
        } else {
            history_id--;
        }
        return getLastAnswerFromHistory();
    }

    public Boolean isHead() {
        if((history.size() - 1) == history_id||history.size()==0){
            return true;
        }
        return false;
    }

    public Boolean isBottom() {
        return isTopStack;
    }

    public HistoryAnswer incrementHistory() {
        if (history.isEmpty()) {
            return null;
        }
        Integer lastId = history.size() - 1;
        if (isTopStack) {
            isTopStack = false;//теперь не первый элемент
            return getLastAnswerFromHistory();
        }
        if (lastId == history_id) {
            return getLastAnswerFromHistory();
        }
        if (lastId > history_id) {
            history_id++;
            return getLastAnswerFromHistory();
        }
        return null;
    }

    public void addAnswerToHistory(HistoryAnswer answer) {
        if (history.size() != 0) {
            isTopStack = false;
            history.subList(history_id + 1, history.size()).clear();//добавил нвый ответ в голову  и убрал все после него
            history_id++;
        }
        history.add(answer);
    }

    public HistoryOfAnswers() {
        history = new ArrayList<>();
        history_id = 0;
        isTopStack = true;
    }
}
