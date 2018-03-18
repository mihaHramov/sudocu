package com.example.miha.sudocu.mvp.data.DP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.miha.sudocu.mvp.data.DP.intf.IRepositoryGame;
import com.example.miha.sudocu.mvp.data.model.Challenge;
import com.example.miha.sudocu.mvp.data.model.Grid;
import com.example.miha.sudocu.mvp.data.model.LocalChallenge;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import rx.Observable;

public class RepositoryGameImplBD extends SQLiteOpenHelper implements IRepositoryGame {

    private static final String nameBD = "myBD";
    private static final String lastChoiceField = "lastChoiceField";
    private static final String complexity = "complexity";
    private static final String name = "name";
    private static final String answer = "answer";
    private static final String challengeTableName = "challengeTable";
    private static final String gameTime = "gameTime";
    private static final String grid = "grid";
    private static final String challengeName = "challengeName";
    private static final String gridId = "gridId";
    private static final String undefined = "undefined";
    private static final String tableName = "myRepository";
    private static final int dbVersion = 7;
    private SQLiteDatabase db;

    public RepositoryGameImplBD(Context context) {
        super(context, nameBD, null, dbVersion);
        db = this.getWritableDatabase();
    }

    @Override
    public Observable<ArrayList<LocalChallenge>> getAllLocalChallenge() {
        String SQL = "SELECT * " + "FROM " + challengeTableName + " inner join " + tableName + " " + "on " + challengeTableName + "." + gridId + " =" + tableName + ".id " + ";";
        ArrayList<LocalChallenge> listOfLocalChallenge = new ArrayList<>();
        Cursor c = db.rawQuery(SQL, null);
        if (c.moveToFirst()) {
            int gridColId = c.getColumnIndex(grid);
            int complexityColId = c.getColumnIndex(complexity);
            int answerColId = c.getColumnIndex(answer);
            int gameTimeColId = c.getColumnIndex(gameTime);
            int undefinedColId = c.getColumnIndex(undefined);
            int lastChoiceCollId = c.getColumnIndex(lastChoiceField);
            int idColId = c.getColumnIndex(gridId);
            int challengeNameColId = c.getColumnIndex("'" + challengeTableName + "'." + challengeName);

            Gson parser = new Gson();
            Type type = new TypeToken<Map<Integer, String>>() {
            }.getType();
            do {
                Grid g = new Grid();
                g.setComplexity(c.getInt(complexityColId));
                g.setGameTime(c.getInt(gameTimeColId));
                g.setUndefined(c.getInt(undefinedColId));
                g.setId(c.getInt(idColId));
                g.setLastChoiseField(c.getInt(lastChoiceCollId));
                String pole = c.getString(gridColId);
                g.setPole(parser.fromJson(pole, String[][].class));
                g.setAnswers(parser.fromJson(c.getString(answerColId), type));
                String name = c.getString(challengeNameColId);
                Challenge challenge = new Challenge(name, "", g);
                LocalChallenge localChallenge = new LocalChallenge(challenge);//тут поправить
                localChallenge.setLocalGame(g);
                listOfLocalChallenge.add(localChallenge);
            } while (c.moveToNext());
        }
        c.close();
        return Observable.just(listOfLocalChallenge);
    }

    @Override
    public Observable<LocalChallenge> getLocalChallenge(Challenge challenge) {
        String SQL = "SELECT * " +
                "FROM " + challengeTableName + " inner join " + tableName + " " + "on " + challengeTableName + "." + gridId + " =" + tableName + ".id "
                + "WHERE "
                + challengeTableName + "." + challengeName + "='" + challenge.getLogin() + "'"
                + " AND " + tableName + "." + name + "='" + challenge.getGrid().getName() + "'"
                + ";";
        LocalChallenge localChallenge = new LocalChallenge(challenge);
        Cursor c = db.rawQuery(SQL, null);
        if (c.moveToFirst()) {
            int gridColId = c.getColumnIndex(grid);
            int complexityColId = c.getColumnIndex(complexity);
            int answerColId = c.getColumnIndex(answer);
            int gameTimeColId = c.getColumnIndex(gameTime);
            int undefinedColId = c.getColumnIndex(undefined);
            int lastChoiceCollId = c.getColumnIndex(lastChoiceField);
            int idColId = c.getColumnIndex(gridId);
            Gson parser = new Gson();
            Type type = new TypeToken<Map<Integer, String>>() {
            }.getType();
            do {
                Grid g = new Grid();
                g.setComplexity(c.getInt(complexityColId));
                g.setGameTime(c.getInt(gameTimeColId));
                g.setUndefined(c.getInt(undefinedColId));
                g.setId(c.getInt(idColId));
                g.setLastChoiseField(c.getInt(lastChoiceCollId));
                String pole = c.getString(gridColId);
                g.setPole(parser.fromJson(pole, String[][].class));
                g.setAnswers(parser.fromJson(c.getString(answerColId), type));
                localChallenge.setLocalGame(g);
                break;
            } while (c.moveToNext());
        }
        c.close();
        return Observable.just(localChallenge);
    }

    @Override
    public Observable<Integer> saveChallenge(String nameChallenge, Integer idGame) {
        return Observable.fromCallable(() -> {
            ContentValues cv = new ContentValues();
            cv.put(challengeName, nameChallenge);
            cv.put(gridId, idGame);
            Long d = db.insert(challengeTableName, null, cv);
            return d.intValue();
        });
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
        return Observable.fromCallable(() -> {
            String id = Long.toString(grid.getId());
            db.delete(tableName, "id = ?", new String[]{id});
            return null;
        });
    }

    private rx.Observable<ArrayList<Grid>> createObserveble(String selection) {
        return Observable.defer(() -> Observable.just(getAllWithWhere(selection, new String[]{"0"})));
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
            Type type = new TypeToken<Map<Integer, String>>() {
            }.getType();
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
                + lastChoiceField + " integer,"
                + answer + " text,"
                + gameTime + " integer,"
                + "name text" + ");");
        db.execSQL("create table " + challengeTableName
                + " ("
                + "id integer primary key autoincrement,"
                + gridId + " integer,"
                + challengeName + " text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table name ");
        db.execSQL("create table "
                + challengeTableName
                + " ("
                + "id integer primary key autoincrement,"
                + gridId + " integer,"
                + challengeName + " text"
                + ");");
    }
}
