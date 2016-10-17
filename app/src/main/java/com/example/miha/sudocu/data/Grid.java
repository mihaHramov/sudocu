package com.example.miha.sudocu.data;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by miha on 17.10.2016.
 */
public class Grid {
    private String[] grid = {"1","2","3","4","5","6","7","8","9"};// основное множество
    private  int razmer = grid.length;
    private  String[][] pole = new String[razmer][razmer];
    public String[][] getGrid(){
        return pole;
    }
    public void  transperentMatrix(){
        for(int i=0;i<razmer;i++)
        {
            for(int j=i;j<razmer;j++)
            {
                String temp = pole[j][i];
                pole[j][i]=pole[i][j];
                pole[i][j]=temp;
            }
        }
    }
    public Grid(){
        //главное сделать сначала первую часть (верхние 3 строки)
        int sdvig = 0;
        for(int i=0;i<razmer/3;i++){
            int temp = 0 + sdvig, j=0;
            while(j < razmer){
                pole[i][j] = grid[temp];
                j++;
                temp++;
                if(temp==razmer){
                    temp = 0;
                }
            }
            sdvig+=3;
        }

        sdvig = 1;
        for(int i=3;i<razmer;i++){
            int temp = 0 + sdvig, j=0;
            while(j < razmer){
                pole[i][j] = pole[i-3][temp];//Arra[temp];
                j++;
                temp++;
                if(temp == razmer){
                    temp = 0;
                }
            }
        }
        transperentMatrix();
    }
}
