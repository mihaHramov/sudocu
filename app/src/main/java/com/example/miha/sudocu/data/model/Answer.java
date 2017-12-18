package com.example.miha.sudocu.data.model;


public class Answer {

    private boolean answer;
    private String number;
    private Integer id;

    public boolean isAnswer() {
        return answer;
    }

    public String getNumber() {
        return number;
    }

    public Answer(String number, boolean answer) {
        this.answer = answer;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
