package com.example.miha.sudocu.data.DP;

public class MementoMainActivity {
    private Integer lastChoseInputId;



    public MementoMainActivity() {
        this.lastChoseInputId = null;
    }

    public Integer getLastChoseInputId() {
        return lastChoseInputId;
    }

    public void setLastChoseInputId(int lastChoseInputId) {
        this.lastChoseInputId = lastChoseInputId;
    }



    public void clear() {
        this.lastChoseInputId = null;
    }
}
