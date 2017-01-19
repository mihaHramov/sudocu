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
        cv.put("name", grid.getName());

        if (grid.getId() == 0) {
            db.insert(tableName, null, cv);
            return;
        }

        db.update(tableName, cv, "id= ?", new String[]{Long.toString(grid.getId())});
        Log.d("mihaHramovRP", grid.toString());
    }


    @Override
    public ArrayList<Grid> getListGames() {
        ArrayList<Grid> arrGrid = new ArrayList<>();
        Cursor c = db.query(tableName, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int nameColId = c.getColumnIndex("name");
            int gridColId = c.getColumnIndex("grid");
            int idColId = c.getColumnIndex("id");
            do {
                Grid g = Grid.getGridByJSON(c.getString(gridColId));
                g.setName(c.getString(nameColId));
                g.setId(c.getInt(idColId));
                if (g.getUndefined() > 0){
                    arrGrid.add(g);
                }
            } while (c.moveToNext());
        }

        return arrGrid;
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
