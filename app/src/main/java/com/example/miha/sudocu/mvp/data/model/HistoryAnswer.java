package com.example.miha.sudocu.mvp.data.model;


import java.util.Map;

public class HistoryAnswer {
    private Integer answerId;
    private String answer;
    private Map<Integer,String> answers;

    public HistoryAnswer(Integer answerId, String answer,Map<Integer,String> answers) {
        this.answerId = answerId;
        this.answers = answers;
        this.answer = answer;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public String getAnswer() {
        return answer;
    }
    public Map<Integer,String> getAnswers(){
        return this.answers;
    }
}
