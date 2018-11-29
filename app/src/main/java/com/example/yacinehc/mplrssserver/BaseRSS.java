package com.example.yacinehc.mplrssserver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseRSS extends SQLiteOpenHelper {

    public final static int VERSION = 1;
    public final static String DB_NAME = "base_rss";
    public final static String RSS_TABLE = "rss";
    public final static String LINK_COLUMN = "link";
    public final static String TITLE_COLUMN = "title";
    public final static String DESCRIPTION_COLUMN = "description";

    public final static String CREATE_RSS = "create table " + RSS_TABLE + "(" +
            LINK_COLUMN + " string primary key, " +
            TITLE_COLUMN + " string, " +
            DESCRIPTION_COLUMN + " string);";

    private static BaseRSS baseRSS;

    public static BaseRSS getInstance(Context context) {
        if (baseRSS == null) {
            baseRSS = new BaseRSS(context);
        }
        return baseRSS;
    }

    private BaseRSS(Context context) {
        super(context, DB_NAME, null, VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RSS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("drop table if exists " + RSS_TABLE);
        }
    }
}
