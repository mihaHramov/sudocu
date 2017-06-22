package com.example.miha.sudocu.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyClass {
    @SerializedName("first")
    @Expose
    private String first;
    @SerializedName("last")
    @Expose
    private String last;

    public String getFirst() {
        return this.first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLast() {
        return this.last;
    }

}
