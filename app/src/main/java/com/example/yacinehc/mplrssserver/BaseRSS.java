package com.example.yacinehc.mplrssserver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseRSS extends SQLiteOpenHelper {

    public final static int VERSION = 7;
    public final static String DB_NAME = "base_rss";
    public final static String RSS_TABLE = "rss";
    public final static String LINK_COLUMN = "link";
    public final static String TITLE_COLUMN = "title";
    public final static String PATH_COLUMN = "path";
    public final static String TIME_COLUMN = "time";

    public final static String DESCRIPTION_COLUMN = "description";


    public final static String ITEMS_TABLE = "items";
    public final static String PUB_DATE_COLUMN = "pubDate";
    public final static String LINK_FOREIGN_KEY_COLUMN = "link_foreign";

    public final static String CREATE_RSS = "create table " + RSS_TABLE + "(" +
            LINK_COLUMN + " string primary key, " +
            TITLE_COLUMN + " string, " +
            DESCRIPTION_COLUMN + " string, " +
            PATH_COLUMN + " string, " +
            TIME_COLUMN + " string);";

    public final static String CREATE_ITEMS = "create table " + ITEMS_TABLE + " (" +
            LINK_COLUMN + " string primary key, " +
            TITLE_COLUMN + " string, " +
            DESCRIPTION_COLUMN + " string, " +
            PUB_DATE_COLUMN + " string, " +
            LINK_FOREIGN_KEY_COLUMN + " string, " +
            "FOREIGN KEY(" + LINK_FOREIGN_KEY_COLUMN + ") REFERENCES " + RSS_TABLE + "(" + LINK_COLUMN + ") " +
            "ON DELETE CASCADE);";

    private static BaseRSS baseRSS;

    private BaseRSS(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static BaseRSS getInstance(Context context) {
        if (baseRSS == null) {
            baseRSS = new BaseRSS(context);
        }
        return baseRSS;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RSS);
        db.execSQL(CREATE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("drop table if exists " + RSS_TABLE);
            db.execSQL("drop table if exists " + ITEMS_TABLE);
            db.execSQL(CREATE_RSS);
            db.execSQL(CREATE_ITEMS);
        }
    }
}
