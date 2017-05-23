package com.example.miha.sudocu.data.DP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.miha.sudocu.data.model.Grid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class RepositoryImplBD extends SQLiteOpenHelper implements IRepository {

    private static final String nameBD = "myBD";
    private static final String complexity = "complexity";
    private static final String name = "name";
    private static final String answer = "answer";
    private static final String gameTime = "gameTime";
    private static final String grid = "grid";
    private static final String undefined = "undefined";
    private static final String tableName = "myRepository";
    private static final int dbVersion = 3;
    private SQLiteDatabase db;

    public RepositoryImplBD(Context context) {
        super(context, nameBD, null, dbVersion);
        db = this.getWritableDatabase();
    }

    @Override
    public void saveGame(Grid g) {
        ContentValues cv = new ContentValues();
        String[][] pole = g.getPole();
        JSONArray poleJsonArray = new JSONArray();
        JSONObject gridAnswer = new JSONObject();
        Map<Integer, String> answers = g.getAnswers();

        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            Integer key = entry.getKey();
            String val = entry.getValue();
            try {
                gridAnswer.put(key + "", val);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < pole.length; i++) {
            for (int j = 0; j < pole.length; j++)
                poleJsonArray.put(pole[i][j]);
        }
        cv.put(grid, poleJsonArray.toString());
        cv.put(complexity, g.getComplexity());
        cv.put(undefined, g.getUndefined());
        cv.put(gameTime, g.getGameTime());
        cv.put(answer, gridAnswer.toString());
        cv.put(name, g.getName());
        if (g.getId() == 0) {
            db.insert(tableName, null, cv);
            return;
        }
        db.update(tableName, cv, "id= ?", new String[]{Long.toString(g.getId())});
    }

    @Override
    public void deleteGame(Grid grid) {
        String id = Long.toString(grid.getId());
        db.delete(tableName,"id = ?",new String[]{id});
    }


    @Override
    public ArrayList<Grid> getListGames() {
        String selection = undefined+" > ?";
        return   getAllWithWhere(selection,new String[]{"0"});
    }

    @Override
    public ArrayList<Grid> getListCompleteGames() {
        String selection = undefined+" < ?";
        return   getAllWithWhere(selection,new String[]{"1"});
    }

    public ArrayList<Grid> getAllWithWhere(String selection,String[] args){
        ArrayList<Grid> arrGrid = new ArrayList<>();
        Cursor c = db.query(tableName, null, selection,args, null, null, null);
        if (c.moveToFirst()) {
            int nameColId = c.getColumnIndex(name);
            int gridColId = c.getColumnIndex(grid);
            int complexityColId = c.getColumnIndex(complexity);
            int answerColId = c.getColumnIndex(answer);
            int gameTimeColId = c.getColumnIndex(gameTime);
            int undefinedColId = c.getColumnIndex(undefined);
            int idColId = c.getColumnIndex("id");
            do {
                Grid g = new Grid();
                g.setComplexity(c.getInt(complexityColId));
                g.setGameTime(c.getInt(gameTimeColId));
                g.setUndefined(c.getInt(undefinedColId));
                g.setName(c.getString(nameColId));
                g.setId(c.getInt(idColId));
                String pole = c.getString(gridColId);
                try {
                    JSONArray jsonObject = new JSONArray(pole);
                    int razmer = (int) Math.sqrt(jsonObject.length());
                    String[][] p = new String[razmer][razmer];
                    for (int i = 0; i < razmer; i++)
                        for (int j = 0; j < razmer; j++)
                            p[i][j] = jsonObject.getString(i * razmer + j);
                    g.setPole(p);

                    JSONObject answ = new JSONObject(c.getString(answerColId));
                    JSONArray arrNames = answ.names();
                    Map<Integer, String> answer = new Hashtable<>();

                    for (int i = 0; i < arrNames.length(); i++)
                        answer.put(arrNames.getInt(i), answ.getString(arrNames.getString(i)));

                    g.setAnswers(answer);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                arrGrid.add(g);
            } while (c.moveToNext());
        }
        c.close();
        return arrGrid;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table " + tableName + " ("
                + "id integer primary key autoincrement,"
                + grid + " text,"
                + complexity + " integer,"
                + undefined + " integer,"
                + answer + " text,"
                + gameTime + " integer,"
                + "name text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + tableName + ";");
        db.execSQL("create table " + tableName + " ("
                + "id integer primary key autoincrement,"
                + grid + " text,"
                + complexity + " integer,"
                + undefined + " integer,"
                + answer + " text,"
                + gameTime + " integer,"
                + "name text" + ");");
    }
}
