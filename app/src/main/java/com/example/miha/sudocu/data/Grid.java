package com.example.miha.sudocu.data;

import android.util.Log;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class Grid implements Serializable {
    Random random = new Random();
    private   int undefined = 5;
    private final int dlinaBloka = 3;
    private String[] grid = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};// основное множество
    private int razmer = grid.length;
    private String[][] pole = new String[razmer][razmer];
    Map<Integer, String> map = new Hashtable<>();

    public int getUndefined() {
        return undefined;
    }

    public Boolean getAnswer(int i, String s) {
        if (map.get(i).equals(s)) {
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
            int temp =  random.nextInt((razmer-1) * (razmer-1));
            map.put(temp, pole[temp / razmer][temp % razmer]);
            pole[temp / razmer][temp % razmer] = "";
        } while (map.size() < undefined);
    }

    public String[][] getGrid() {
        return pole;
    }

    //Обмен двух строк в пределах одного района (swap_rows_small)
    private void swap_rows_small() {
        int rajon = random.nextInt(dlinaBloka) * dlinaBloka;
        int stingOne = random.nextInt(dlinaBloka) + rajon;//1+0
        int stringTwo = random.nextInt(dlinaBloka) + rajon;//2+0
        show(stingOne);
        show(stringTwo);
        for (int j = 0; j < razmer; j++) {
            String temp = pole[stingOne][j];
            pole[stingOne][j] = pole[stringTwo][j];
            pole[stringTwo][j] = temp;
        }
    }

    private void show(int i) {
        Log.d("_miha", i + "");
    }

    private void swap_rows_area() {
        int rajonOne = random.nextInt(dlinaBloka) + 1; //рандомно выбрали район
        int rajonTwo = random.nextInt(dlinaBloka) + 1; //рандомно выбрали второй
        //поменяли местами
        show(rajonOne);
        show(rajonTwo);
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

    private void swap_colums_area() {
        int rajonOne = random.nextInt(dlinaBloka) + 1; //рандомно выбрали район
        int rajonTwo = random.nextInt(dlinaBloka) + 1;        //рандомно выбрали второй
        show(rajonOne);
        show(rajonTwo);
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
    private void swap_colums_small() {
        int rajon = random.nextInt(dlinaBloka);//получил район
        int leftBottom = rajon * dlinaBloka;//leftBottom 0*3 = 0
        int stingOne = random.nextInt(dlinaBloka) + leftBottom;
        //взять рандомно столбец
        int stringTwo = random.nextInt(dlinaBloka) + leftBottom;
        show(rajon);
        show(stingOne);
        show(stringTwo);
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
                swap_rows_small();//готовро
                swap_rows_area();//готово
                swap_colums_small();//готово
                swap_colums_area();
                iter--;
            }
            while (iter > 0);
        }
        initAnswer();
    }
}
