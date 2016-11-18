package com.example.miha.sudocu.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.miha.sudocu.data.IData.ISettingComplexity;

public class SettingComplexity implements ISettingComplexity {
    private final String nameOfFile = "tempFileName";
    private final String Complexity = "Complexity";
    SharedPreferences ch;

    public SettingComplexity(Context c) {
        ch = c.getSharedPreferences(nameOfFile, Context.MODE_PRIVATE);
    }

    @Override
    public void save(int i) {
        SharedPreferences.Editor ed = ch.edit();
        ed.putInt(Complexity,i);
        ed.commit();
    }

    @Override
    public int load() {
        return ch.getInt(Complexity, 0);
    }
}
