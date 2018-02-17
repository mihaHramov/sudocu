package com.example.miha.sudocu.mvp.data.model;


public class HistoryAnswer {
    private Integer answerId;
    private String answer;

    public HistoryAnswer(Integer answerId, String answer) {
        this.answerId = answerId;
        this.answer = answer;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public String getAnswer() {
        return answer;
    }
}
