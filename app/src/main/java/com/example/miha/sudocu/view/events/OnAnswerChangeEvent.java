package com.example.miha.sudocu.view.events;

public class OnAnswerChangeEvent {
    private String answer;

    public OnAnswerChangeEvent(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}
