package com.example.miha.sudocu.view.events;

public class onAnswerChangeEvent {
    private String answer;

    public onAnswerChangeEvent(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}
