package com.example.miha.sudocu.data.model;


public class Answer {

    private boolean answer;
    private String number;

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public String getNumber() {
        return number;
    }

    public Answer(String number, boolean answer) {
        this.answer = answer;
        this.number = number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
