package com.example.yacinehc.mplrssserver;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    private static final String LOG = "MyContentProvider";
    private final static String authority = "fr.diderot.yacinehc.mplrssserver";
    private final static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private final static int CODE_RSS = 1;

    static {
        matcher.addURI(authority, "rss", CODE_RSS);
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
        String path=null;
        long id = -1;
        try {
            switch (code) {
                case CODE_RSS:
                    id = db.insertOrThrow(BaseRSS.RSS_TABLE, null, values);
                    path = "rss";
                    break;
                default:
                    throw new UnsupportedOperationException("Not yet implemented");
            }
        }
        catch (SQLException e){
            Log.d(LOG,"Erreur SQL",e);
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
        Cursor cursor=null;
        try{
        switch (code) {
            case CODE_RSS:
                cursor = db.query(BaseRSS.RSS_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
            }


        }
        catch (SQLException e){
            Log.d(LOG,"Erreur Requete "+e.getMessage());
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
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
