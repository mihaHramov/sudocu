package com.example.miha.sudocu.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by miha on 15.01.17.
 */

public class RepositoryImplBD extends SQLiteOpenHelper implements IRepository {

    private static final String nameBD = "myBD";
    private static final String tableName = "myRepository";
    private SQLiteDatabase db;
    private ContentValues cv = new ContentValues();

    public RepositoryImplBD(Context context) {
        super(context, nameBD, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void saveGame(Grid grid) {
        cv.put("grid", grid.toString());
        cv.put("name", "vasa");
        //  Log.d("mihaHramov",
        db.insert(tableName, null, cv);//+"");
        // Log.d("mihaSave",grid.toString());
        // Grid.getGridByJSON(grid.toString());
    }


    @Override
    public ArrayList<Grid> getListGames() {
        ArrayList<Grid> arrGrid = new ArrayList<>();
        Cursor c = db.query(tableName, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int nameColId = c.getColumnIndex("name");
            int gridColId = c.getColumnIndex("grid");
            do {
                Log.d("mihaHramov", c.getString(gridColId));
                arrGrid.add(Grid.getGridByJSON(c.getString(gridColId)));
            } while (c.moveToNext());
        }

        return arrGrid;
    }

    @Override
    public Grid loadGame(Grid json) {
        return Grid.getGridByJSON("");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table " + tableName + " ("
                + "id integer primary key autoincrement,"
                + "grid text,"
                + "name text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
