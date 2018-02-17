package com.example.miha.sudocu.mvp.view.events;

import java.util.Map;



public class OnChangeCountOfAnswer {
    private final Map<String, Integer> countOfAnswers;

    public OnChangeCountOfAnswer(Map<String,Integer> countOfAnswers) {
        this.countOfAnswers = countOfAnswers;
    }

    public Map<String, Integer> getCountOfAnswers() {
        return countOfAnswers;
    }
}
