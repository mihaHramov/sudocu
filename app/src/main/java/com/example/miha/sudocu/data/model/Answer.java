package com.example.miha.sudocu.data.model;


public class Answer {

    private Boolean answer;
    private String number;
    private Integer id;

    public Boolean isAnswer() {
        return answer;
    }

    public String getNumber() {
        return number;
    }

    public Integer getId() {
        return id;
    }

    public Answer(String number, Boolean answer) {
        this.answer = answer;
        this.number = number;
    }

    public Answer(String number, Boolean answer,Integer id) {
        this.answer = answer;
        this.id = id;
        this.number = number;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
