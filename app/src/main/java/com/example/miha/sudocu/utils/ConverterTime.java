package com.example.miha.sudocu.utils;


public class ConverterTime {
    private static ConverterTime instance;

    public static ConverterTime getInstance() {
        if (instance == null) instance = new ConverterTime();
        return instance;
    }

    public Long converterLongToMinutes(Long time) {
        return time / 60;
    }

    public Long converterLongToSeconds(Long time) {
        return time % 60;
    }

    private ConverterTime() {
    }
}
