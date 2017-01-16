package com.example.miha.sudocu.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class Grid implements Serializable {
    Random random = new Random();
    private int undefined;
    private long id = 0;
    private int complexity;
    private final int dlinaBloka = 3;
    private String[] grid = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};// основное множество
    private int razmer = grid.length;
    private String[][] pole = new String[razmer][razmer];
    private Map<Integer, String> answers = new Hashtable<>();

    public static final String KEY ="Grid";
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setAnswers(Map<Integer, String> answers) {
        this.answers = answers;
    }


    public void setPole(String[][] pole) {
        this.pole = pole;
    }


    public void setComplexity(int complex) {
        complexity = complex;
        undefined = complex;
    }

    public static Grid getGridByJSON(String json) {
        Grid grid = new Grid();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject answ = jsonObject.getJSONObject("answers");
            JSONArray arrNames = answ.names();
            Map<Integer, String> answerMap = new Hashtable<>();
            int razmer = (int) Math.sqrt(jsonObject.getJSONArray("grid").length());
            String[][] p = new String[razmer][razmer];

            for (int i = 0; i < arrNames.length(); i++)
                answerMap.put(arrNames.getInt(i), answ.getString(arrNames.getString(i)));

            for (int i = 0; i < razmer; i++)
                for (int j = 0; j < razmer; j++)
                    p[i][j] = jsonObject.getJSONArray("grid").getString(i * razmer + j);

            grid.setAnswers(answerMap);
            grid.setUndefined(jsonObject.getInt("undefined"));
            grid.setPole(p);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return grid;
    }

    @Override
    public String toString() {

        JSONObject an = new JSONObject();
        JSONObject gridAnswer = new JSONObject();
        JSONArray pupilsArray = new JSONArray();

        for (int i = 0; i < razmer; i++) {
            for (int j = 0; j < razmer; j++)
                pupilsArray.put(pole[i][j]);
        }

        try {

            for (Map.Entry<Integer, String> entry : answers.entrySet()) {
                Integer key = entry.getKey();
                String val = entry.getValue();
                Log.d("mihaAnswer", key + ":" + val);
                gridAnswer.put(key + "", val);
            }
            an.put("undefined", getUndefined());
            an.put("answers", gridAnswer);
            an.put("grid", pupilsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return an.toString();
    }

    public void setUndefined(int undefined) {
        this.undefined = undefined;
    }

    public int getUndefined() {
        return undefined;
    }

    public Boolean getAnswer(int i, String s) {
        if (answers.get(i).equals(s)) {
            int str = i / razmer;
            int col = i % razmer;
            pole[str][col] = s;
            undefined--;
            return true;
        }
        return false;
    }

    private void initAnswer() {
        do {
            int temp = random.nextInt((razmer - 1) * (razmer - 1));
            if (answers.get(temp) != null) continue;
            answers.put(temp, pole[temp / razmer][temp % razmer]);
            pole[temp / razmer][temp % razmer] = "";
        } while (answers.size() < undefined);
    }


    public String[][] getGrid() {
        return pole;
    }

    //Обмен двух строк в пределах одного района (swapRowsSmall)
    private void swapRowsSmall() {
        int rajon = random.nextInt(dlinaBloka) * dlinaBloka;
        int stingOne = random.nextInt(dlinaBloka) + rajon;//1+0
        int stringTwo = random.nextInt(dlinaBloka) + rajon;//2+0
        for (int j = 0; j < razmer; j++) {
            String temp = pole[stingOne][j];
            pole[stingOne][j] = pole[stringTwo][j];
            pole[stringTwo][j] = temp;
        }
    }

    private void swapRowsArea() {
        int rajonOne = random.nextInt(dlinaBloka) + 1; //рандомно выбрали район
        int rajonTwo = random.nextInt(dlinaBloka) + 1; //рандомно выбрали второй

        int rajonOneBottom = rajonOne * dlinaBloka;
        int rajonOneTop = rajonOneBottom - dlinaBloka;
        // нужно вычислить шаг
        int shag = (rajonTwo * dlinaBloka - dlinaBloka) - rajonOneTop;

        for (int i = rajonOneTop; i < rajonOneBottom; i++) {
            for (int j = 0; j < razmer; j++) {
                String temp = pole[i][j];
                pole[i][j] = pole[i + shag][j];
                pole[i + shag][j] = temp;
            }
        }
    }

    private void swapColumsArea() {
        int rajonOne = random.nextInt(dlinaBloka) + 1; //рандомно выбрали район
        int rajonTwo = random.nextInt(dlinaBloka) + 1;        //рандомно выбрали второй
        int rajonOneRight = rajonOne * dlinaBloka;
        int rajonOneLeft = rajonOneRight - dlinaBloka;//9-3=6

        int rajonTwoRight = rajonTwo * dlinaBloka;//3*1=3
        int rajonTwoLeft = rajonTwoRight - dlinaBloka;//3-3=0
        int shag = rajonTwoLeft - rajonOneLeft;//0-6=-6

        for (int i = 0; i < razmer; i++) {
            for (int j = rajonOneLeft; j < rajonOneRight; j++) {
                String temp = pole[i][j];
                pole[i][j] = pole[i][j + shag];
                pole[i][j + shag] = temp;
            }
        }

    }

    //объмен столбцов в
    private void swapColumsSmall() {
        int rajon = random.nextInt(dlinaBloka);//получил район
        int leftBottom = rajon * dlinaBloka;//leftBottom 0*3 = 0
        int stingOne = random.nextInt(dlinaBloka) + leftBottom;
        //взять рандомно столбец
        int stringTwo = random.nextInt(dlinaBloka) + leftBottom;
        for (int i = 0; i < razmer; i++) {
            String temp = pole[i][stingOne];
            pole[i][stingOne] = pole[i][stringTwo];
            pole[i][stringTwo] = temp;
        }
    }


    private void transperentMatrix() {
        for (int i = 0; i < razmer; i++) {
            for (int j = i; j < razmer; j++) {
                String temp = pole[j][i];
                pole[j][i] = pole[i][j];
                pole[i][j] = temp;
            }
        }
    }


    public void init() {
        //главное сделать сначала первую часть (верхние 3 строки)
        int sdvig = 0;
        for (int i = 0; i < razmer / dlinaBloka; i++) {
            int temp = 0 + sdvig, j = 0;
            while (j < razmer) {
                pole[i][j] = grid[temp];
                j++;
                temp++;
                if (temp == razmer) {
                    temp = 0;
                }
            }
            sdvig += dlinaBloka;
        }

        sdvig = 1;
        for (int i = dlinaBloka; i < razmer; i++) {
            int temp = 0 + sdvig, j = 0;
            while (j < razmer) {
                pole[i][j] = pole[i - dlinaBloka][temp];//Arra[temp];
                j++;
                temp++;
                if (temp == razmer) {
                    temp = 0;
                }
            }
        }
        Random j = new Random();
        int temp = j.nextInt(10) + 10;
        for (int i = 0; i < temp; i++) {
            int iter = j.nextInt(temp) + i;
            do {
                transperentMatrix();//готово
                swapRowsSmall();//готовро
                swapRowsArea();//готово
                swapColumsSmall();//готово
                swapColumsArea();
                iter--;
            }
            while (iter > 0);
        }
        initAnswer();
    }
}
