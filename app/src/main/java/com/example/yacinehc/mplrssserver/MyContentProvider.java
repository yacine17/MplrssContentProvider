package com.example.yacinehc.mplrssserver;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    private final static String authority = "fr.diderot.yacinehc.mplrssserver";
    private final static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private final static int CODE_RSS = 1;
    private final static int CODE_ITEM = 2;

    static {
        matcher.addURI(authority, "rss", CODE_RSS);
        matcher.addURI(authority, "items", CODE_ITEM);
    }

    private BaseRSS baseRSS;

    public MyContentProvider() {
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = baseRSS.getWritableDatabase();
        int code = matcher.match(uri);
        String path = "";
        long id = -1;
        switch (code) {
            case CODE_RSS:
                try {
                    id = db.insertOrThrow(BaseRSS.RSS_TABLE, null, values);
                    path = "rss";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CODE_ITEM:
                try {
                    id = db.insertOrThrow(BaseRSS.ITEMS_TABLE, null, values);
                    path = "items";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        Uri.Builder builder = (new Uri.Builder()).authority(authority).appendPath(path);
        return ContentUris.appendId(builder, id).build();
    }

    @Override
    public boolean onCreate() {
        baseRSS = BaseRSS.getInstance(this.getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = baseRSS.getReadableDatabase();
        int code = matcher.match(uri);
        Cursor cursor;
        switch (code) {
            case CODE_RSS:
                cursor = db.query(BaseRSS.RSS_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case CODE_ITEM:
                cursor = db.query(BaseRSS.ITEMS_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = baseRSS.getWritableDatabase();
        int code = matcher.match(uri);
        switch (code) {
            case CODE_RSS:
                return db.delete(BaseRSS.RSS_TABLE, selection, selectionArgs);
            case CODE_ITEM:
                return db.delete(BaseRSS.ITEMS_TABLE, selection, selectionArgs);
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
