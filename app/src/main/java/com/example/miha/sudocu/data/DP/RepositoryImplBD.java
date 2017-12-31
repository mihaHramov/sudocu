package com.example.miha.sudocu.data.DP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.miha.sudocu.data.DP.intf.IRepository;
import com.example.miha.sudocu.data.model.Grid;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import rx.Observable;

public class RepositoryImplBD extends SQLiteOpenHelper implements IRepository {

    private static final String nameBD = "myBD";
    private static final String lastChoiceField = "lastChoiceField";
    private static final String complexity = "complexity";
    private static final String name = "name";
    private static final String answer = "answer";
    private static final String gameTime = "gameTime";
    private static final String grid = "grid";
    private static final String undefined = "undefined";
    private static final String tableName = "myRepository";
    private static final int dbVersion = 5;
    private SQLiteDatabase db;

    public RepositoryImplBD(Context context) {
        super(context, nameBD, null, dbVersion);
        db = this.getWritableDatabase();
    }

    @Override
    public Observable<Integer> saveGame(Grid g) {
        return Observable.create(subscriber -> {
            ContentValues cv = new ContentValues();
            Gson parser = new Gson();

            cv.put(grid, parser.toJson(g.getGrid()));
            cv.put(lastChoiceField, g.getLastChoiseField());
            cv.put(complexity, g.getComplexity());
            cv.put(undefined, g.getUndefined());
            cv.put(gameTime, g.getGameTime());
            cv.put(answer, parser.toJson(g.getAnswers()));
            cv.put(name, g.getName());
            if (g.getId() == 0) {
                Long d = db.insert(tableName, null, cv);
                subscriber.onNext(d.intValue());
                g.setId(d);
                subscriber.onCompleted();
                return;
            }
            subscriber.onNext(db.update(tableName, cv, "id= ?", new String[]{Long.toString(g.getId())}));
            subscriber.onCompleted();
        });

    }

    @Override
    public Observable<Void> deleteGame(Grid grid) {
        return Observable.create(subscriber -> {
            String id = Long.toString(grid.getId());
            db.delete(tableName, "id = ?", new String[]{id});
            subscriber.onCompleted();
        });
    }

    private rx.Observable<ArrayList<Grid>> createObserveble(String selection) {
        return rx.Observable.create(
                sub -> {
                    sub.onNext(getAllWithWhere(selection, new String[]{"0"}));
                    sub.onCompleted();
                }
        );
    }

    @Override
    public rx.Observable<ArrayList<Grid>> getListGames() {
        return createObserveble(undefined + " > ?");
    }

    @Override
    public rx.Observable<ArrayList<Grid>> getListCompleteGames() {
        return createObserveble(undefined + " <= ?");
    }

    private ArrayList<Grid> getAllWithWhere(String selection, String[] args) {
        ArrayList<Grid> arrGrid = new ArrayList<>();

        Cursor c = db.query(tableName, null, selection, args, null, null, null);
        if (c.moveToFirst()) {
            int nameColId = c.getColumnIndex(name);
            int gridColId = c.getColumnIndex(grid);
            int complexityColId = c.getColumnIndex(complexity);
            int answerColId = c.getColumnIndex(answer);
            int gameTimeColId = c.getColumnIndex(gameTime);
            int undefinedColId = c.getColumnIndex(undefined);
            int lastChoiceCollId = c.getColumnIndex(lastChoiceField);
            int idColId = c.getColumnIndex("id");
            Gson parser = new Gson();
            Type type = new TypeToken<Map<Integer, String>>(){}.getType();
            do {
                Grid g = new Grid();
                g.setComplexity(c.getInt(complexityColId));
                g.setGameTime(c.getInt(gameTimeColId));
                g.setUndefined(c.getInt(undefinedColId));
                g.setName(c.getString(nameColId));
                g.setId(c.getInt(idColId));
                g.setLastChoiseField(c.getInt(lastChoiceCollId));
                String pole = c.getString(gridColId);
                g.setPole(parser.fromJson(pole, String[][].class));
                g.setAnswers(parser.fromJson(c.getString(answerColId), type));
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
                + lastChoiceField+" integer,"
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
                + lastChoiceField +" integer,"
                + gameTime + " integer,"
                + "name text" + ");");
    }

}
