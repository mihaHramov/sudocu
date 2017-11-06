package com.example.miha.sudocu.data.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import javax.annotation.Generated;

import com.example.miha.sudocu.data.DP.IGenerateGame;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Grid implements Serializable {
    private transient Random random = new Random();
    private int razmer;

    @SerializedName("undefined")
    @Expose
    private int undefined;

    @SerializedName("gameTime")
    @Expose
    private long gameTime = 0;

    @SerializedName("id")
    @Expose
    private long id = 0;

    @SerializedName("name")
    @Expose
    private String name = "";

    @SerializedName("complexity")
    @Expose
    private int complexity;

    @SerializedName("pole")
    @Expose
    private String[][] pole;

    @SerializedName("answers")
    @Expose
    private Map<Integer, String> answers = new Hashtable<>();

    public static final String KEY = "Grid";

    public long getGameTime() {
        return gameTime;
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setAnswers(Map<Integer, String> answersMap) {
        answers = answersMap;
    }


    public void setPole(String[][] pole) {
        this.pole = pole;
    }


    public Grid setComplexity(int complex) {
        complexity = complex;
        return this;
    }

    public String[][] getPole() {
        return pole;
    }


    public Map<Integer, String> getAnswers() {
        return answers;
    }

    public Grid setUndefined(int undefined) {
        this.undefined = undefined;
        return this;
    }

    public int getUndefined() {
        return undefined;
    }

    public Boolean getAnswer(int id, String s) {
        int str = id / pole.length;
        int col = id % pole.length;
        pole[str][col] = s;
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            int i = entry.getKey() / pole.length;
            int j = entry.getKey() % pole.length;
            if (!entry.getValue().equals(pole[i][j])) {
                return false;
            }
        }
        undefined = 0;
        return true;
    }

    public Answer[][] getGameGrid() {
        razmer = pole.length;
        Answer[][] answer = new Answer[razmer][razmer];
        for (int i = 0; i < razmer; i++) {
            for (int j = 0; j < razmer; j++) {
                answer[i][j] = new Answer(pole[i][j], answers.containsKey(i * razmer + j));
            }
        }
        return answer;
    }
    public String[][] getGrid() {
        return pole;
    }

    public Grid init(IGenerateGame generateGame) {
        pole = generateGame.generateGame();
        razmer = pole.length;
        answers = generateGame.initAnswer(getUndefined());

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        name = sdf.format(date);
        return this;
    }
}