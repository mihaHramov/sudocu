package com.example.miha.sudocu.data.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private Integer history_id;

    @SerializedName("lastChoiseField")
    @Expose
    private Integer lastChoiseField;

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

    @SerializedName("history")
    @Expose
    private ArrayList<HistoryAnswer> history;

    public static final String KEY = "Grid";

    public Integer getLastChoiseField() {
        return lastChoiseField;
    }

    public void setLastChoiseField(Integer lastChoiseField) {
        this.lastChoiseField = lastChoiseField;
    }

    public void addAnswerToHistory(HistoryAnswer answer) {
        if ((history_id != null) && history_id != history.size() - 1) {//если не последний
            history.subList(history_id + 1, history.size()).clear();
            history_id = null;
        }
        history.add(answer);
    }

    public HistoryAnswer incrementHistory() {
        if (history_id == null) {
            history_id = 0;
        }
        if (history.size() == history_id + 1 || history.size() == 0) return null;
        history_id++;
        return getLastAnswerFromHistory();
    }

    public HistoryAnswer decrementHistory() {
        if (history_id == null) {
            history_id = history.size() - 1;
        }
        if (history_id == 0 || history.size() == 0) return null;
        history_id--;
        return getLastAnswerFromHistory();
    }

    private HistoryAnswer getLastAnswerFromHistory() {
        return history.get(history_id);
    }

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

    public void deleteAnswer(Answer answer){
        int str = answer.getId() / pole.length;
        int col = answer.getId() % pole.length;
        pole[str][col] = answer.getNumber();
    }
    public int getUndefined() {
        return undefined;
    }

    public void setAnswer(int id, String s) {
        int str = id / pole.length;
        int col = id % pole.length;
        pole[str][col] = s;
    }

    public Boolean isGameOver() {
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

    public Grid() {
        history = new ArrayList<>();
        history_id = null;
    }

    public ArrayList<Integer> getTheSameAnswers(Integer id) {
        ArrayList<Integer> sameAnswers = new ArrayList<>();
        if (id == null) return sameAnswers;
        int k = id / razmer, l = id % razmer;
        for (int i = 0; i < razmer; i++) {
            for (int j = 0; j < razmer; j++) {
                if (i == k && j == l) {
                    continue;
                }
                if (pole[k][l].equalsIgnoreCase(pole[i][j])) {
                    sameAnswers.add(i * razmer + j);
                }
            }
        }
        return sameAnswers;
    }

    public ArrayList<Integer> getErrors() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            arrayList.addAll(getErrors(entry.getKey()));
        }
        return arrayList;
    }

    public ArrayList<Integer> getErrors(Integer id) {

        ArrayList<Integer> sameErrors = new ArrayList<>();
        if (id == null) return sameErrors;
        razmer = pole.length;
        int k = id / razmer, l = id % razmer;
        if (pole[k][l].isEmpty()) return sameErrors;
        for (int i = 0; i < razmer; i++) {//
            if (i == k) continue;
            if (pole[i][l].equalsIgnoreCase(pole[k][l]) && !sameErrors.contains(i * razmer + l)) {//по строкам
                sameErrors.add(i * razmer + l);
            }
        }

        for (int j = 0; j < razmer; j++) {
            if (j == l) continue;
            if (pole[k][j].equalsIgnoreCase(pole[k][l]) && !sameErrors.contains(k * razmer + j)) {
                sameErrors.add(k * razmer + j);
            }
        }

        int i = (k / 3) * 3;
        int j = (l / 3) * 3;

        for (int count = 0; count < 3; count++) {
            for (int countJ = 0; countJ < 3; countJ++) {

                if ((i + count) == k && (j + countJ) == l) continue;
                int temp = (i + count) * razmer + j + countJ;

                if (pole[k][l].equalsIgnoreCase(pole[i + count][j + countJ])) {
                    if (!sameErrors.contains(temp)) {
                        sameErrors.add(temp);
                    }
                }
            }
        }
        if (!sameErrors.isEmpty()) {
            sameErrors.add(id);
        }
        return sameErrors;
    }

    public ArrayList<Integer> getKnowOptions(Integer id) {
        ArrayList<Integer> knowOption = new ArrayList<>();
        if (id == null) return knowOption;
        int k = id / razmer, l = id % razmer;
        for (int i = 0; i < razmer; i++) {//
            if (i == k) continue;
            if (answers.get(i * razmer + l) == null) {//по строкам
                knowOption.add(i * razmer + l);
            }
        }

        for (int j = 0; j < razmer; j++) {
            if (j == l) continue;
            if (answers.get(k * razmer + j) == null) {
                knowOption.add(k * razmer + j);
            }
        }

        int i = (k / 3) * 3;
        int j = (l / 3) * 3;

        for (int count = 0; count < 3; count++) {
            for (int countJ = 0; countJ < 3; countJ++) {
                int temp = (i + count) * razmer + j + countJ;
                if (answers.get(temp) == null) {
                    knowOption.add(temp);
                }
            }
        }

        return knowOption;
    }

    public Boolean isAnswer(int id) {
        return answers.containsKey(id);
    }

    public String getAnswer(int id) {
        int k = id / razmer, l = id % razmer;
        return pole[k][l];
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

    public Map<String, Integer> getCountOfAnswers() {
        Map<String, Integer> countOfAnswers = new Hashtable<>();
        for (int i = 0; i < pole.length; i++)
            for (int j = 0; j < pole.length; j++) {
                String answer = pole[i][j];
                if (answer.trim().isEmpty()) continue;
                Integer count = 0;
                if (countOfAnswers.containsKey(answer)) {
                    count = countOfAnswers.get(answer);
                }
                count++;
                countOfAnswers.put(answer, count);
            }
        return countOfAnswers;
    }

    public void replayGame() {
        Integer length = pole.length;
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            pole[entry.getKey() / length][entry.getKey() % length] = "";
        }
        setGameTime(0);
        setLastChoiseField(null);
    }
}