package com.example.miha.sudocu.data.model;


import java.io.Serializable;
import java.util.ArrayList;

public class HistoryOfAnswers implements Serializable {
    private Boolean isTopStack;
    private Integer history_id;
    private ArrayList<HistoryAnswer> history;

    private HistoryAnswer getLastAnswerFromHistory() {
        return history.get(history_id);
    }

    private Boolean isEmptyHistory() {
        return history.size() == 0;
    }

    public HistoryAnswer decrementHistory() {
        if (isEmptyHistory() || history_id == 0){
            isTopStack = true;
            return null;
        }
        history_id--;
        return getLastAnswerFromHistory();
    }

    public HistoryAnswer incrementHistory() {
        HistoryAnswer temp = null;
        if (!isEmptyHistory()) {

            if (isTopStack && history_id < history.size() - 1) {//если первый элемент и не последний
                temp = getLastAnswerFromHistory();
                isTopStack = false;
            }else if(history_id < history.size() - 1){//если не первый и не последний
                history_id++;
                temp = getLastAnswerFromHistory();
            }
        }
        return temp;
    }

    public void addAnswerToHistory(HistoryAnswer answer) {
        if (history.size() != 0) {
            history.subList(history_id + 1, history.size()).clear();
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
