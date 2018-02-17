package com.example.miha.sudocu.utils;

import android.content.Intent;

import com.example.miha.sudocu.data.DP.GenerateGame;
import com.example.miha.sudocu.data.model.Grid;
import com.example.miha.sudocu.view.Adapter.AlertDialog;
import com.google.gson.Gson;


public class SerializableGame {
    public static String serializable(Grid grid) {
        Gson gson = new Gson();
        return gson.toJson(grid);
    }

    public static Grid unSerializable(Intent intent) {
        Gson gson = new Gson();
        Grid grid = gson.fromJson(intent.getStringExtra(Grid.KEY), Grid.class);
        intent.removeExtra(Grid.KEY);
        if(grid == null){
            int complex = intent.getIntExtra(AlertDialog.SETTINGS, 1);
            grid = new Grid().setComplexity(complex).setUndefined(complex).init(new GenerateGame());
        }
        return grid;
    }
}
