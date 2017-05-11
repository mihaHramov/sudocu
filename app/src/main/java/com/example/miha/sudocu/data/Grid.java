package com.example.miha.sudocu.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class Grid implements Serializable {
    private Random random = new Random();
    private int undefined;
    private long gameTime = 0;
    private long id = 0;
    private String name = "";
    private int complexity;
    private final int dlinaBloka = 3;
    private String[] grid = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};// основное множество
    private int razmer = grid.length;
    private String[][] pole = new String[razmer][razmer];
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

    public Boolean getAnswer(int i, String s) {

        if (answers.get(i).equals(s)) {

            answers.put(i, "");
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


    public Grid init() {
        //главное сделать сначала первую часть (верхние 3 строки)
        int sdvig = 0;
        for (int i = 0; i < razmer / dlinaBloka; i++) {
            int temp = sdvig, j = 0;
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
            int temp =  sdvig, j = 0;
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

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        name = sdf.format(date);
        return this;
    }
}
