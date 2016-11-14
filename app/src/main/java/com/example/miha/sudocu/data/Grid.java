package com.example.miha.sudocu.data;

import android.util.Log;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by miha on 17.10.2016.
 */
public class Grid implements Serializable {
    private int undefined = 2;
    private String[] grid = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};// основное множество
    private int razmer = grid.length;
    private String[][] pole = new String[razmer][razmer];

    public int getUndefined() {
        return undefined;
    }

    public Boolean getAnsver(int i, int j, String s) {
        if (pole[i][j].equals(s)) {
            undefined--;
            return true;
        }
        return false;
    }

    public String[][] getGrid() {
        String[][] s = new String[razmer][razmer];
        for (int i = 0; i < razmer; i++) {
            for (int j = 0; j < razmer; j++) {
                s[i][j] = pole[i][j];
            }
        }
        s[1][1] = "";//пока не придумал как будит растановка пустых ячеек
        s[2][2] = "";
        return s;
    }

    //Обмен двух строк в пределах одного района (swap_rows_small)
    private void swap_rows_small() {
        int stingOne = 0;
        int stringTwo = 1;
        for (int j = 0; j < razmer; j++) {
            String temp = pole[stingOne][j];
            pole[stingOne][j] = pole[stringTwo][j];
            pole[stringTwo][j] = temp;
        }
    }

    private void swap_rows_area() {
        int rajonOne = 1; //рандомно выбрали район
        int rajonTwo = 3;        //рандомно выбрали второй
        //поменяли местами
        int rajonOneBottom = rajonOne * 3;//1*3 = 3
        int rajonOneTop = rajonOneBottom - 3;// 3 - 3 =0
        // нужно вычислить шаг
        int shag = (rajonTwo * 3 - 3) - rajonOneTop;//3*3 = 9 -3 =6 -0 = 6

        for (int i = rajonOneTop; i < rajonOneBottom; i++) {
            for (int j = 0; j < razmer; j++) {
                String temp = pole[i][j];
                pole[i][j] = pole[i + shag][j];
                pole[i + shag][j] = temp;
            }
        }
    }

    private void swap_colums_area() {
        int rajonOne = 3; //рандомно выбрали район
        int rajonTwo = 1;        //рандомно выбрали второй
        int rajonOneRight = rajonOne * 3;//3*3=9
        int rajonOneLeft = rajonOneRight-3;//9-3=6

        int rajonTwoRight = rajonTwo*3;//3*1=3
        int rajonTwoLeft = rajonTwoRight - 3;//3-3=0
        int shag = rajonTwoLeft - rajonOneLeft;//0-6=-6

        for(int i =0;i<razmer;i++){
            for(int j =rajonOneLeft;j<rajonOneRight;j++ ){
                String temp = pole[i][j];
                pole[i][j] = pole[i][j+shag];
                pole[i][j+shag]= temp;
            }
        }

    }

    private void swap_colums_small() {
        //взять рандомно район
        // взять в нем рандомно столбцы
        int stingOne = 0;
        int stringTwo = 1;


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

    public Grid() {
        //главное сделать сначала первую часть (верхние 3 строки)
        int sdvig = 0;
        for (int i = 0; i < razmer / 3; i++) {
            int temp = 0 + sdvig, j = 0;
            while (j < razmer) {
                pole[i][j] = grid[temp];
                j++;
                temp++;
                if (temp == razmer) {
                    temp = 0;
                }
            }
            sdvig += 3;
        }

        sdvig = 1;
        for (int i = 3; i < razmer; i++) {
            int temp = 0 + sdvig, j = 0;
            while (j < razmer) {
                pole[i][j] = pole[i - 3][temp];//Arra[temp];
                j++;
                temp++;
                if (temp == razmer) {
                    temp = 0;
                }
            }
        }
        // transperentMatrix();
        // swap_rows_small();
        //   swap_colums_small();
        // swap_rows_area();
        //swap_rows_area();

        swap_colums_area();
    }
}
