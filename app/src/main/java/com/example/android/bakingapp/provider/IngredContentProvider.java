package com.example.android.bakingapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.bakingapp.provider.IngredContract;
import com.example.android.bakingapp.provider.IngredContract.IngredEntry;

public class IngredContentProvider extends ContentProvider{
    public static final int INGRED = 100;
    private static final UriMatcher sUriMatcher =buildUriMatcher();
    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(IngredContract.AUTHORITY, IngredContract.PATH_INGRED, INGRED);
        return uriMatcher;
    }

    ///private IngredDbHelper mIngredDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
       /// mIngredDbHelper = new IngredDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues cv) {
        /*
        final SQLiteDatabase db = mIngredDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case INGRED:
                long id = db.insert(IngredEntry.TABLE_NAME, null, cv);
                if(id>0){
                    returnUri = ContentUris.withAppendedId(IngredContract.BASE_CONTENT_URI, id);
                }else {
                    throw new SQLException("didn't insert db");
                }
                break;
            default:
                throw new UnsupportedOperationException("default switch");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
        */
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        /*
        final SQLiteDatabase db = mIngredDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match){
            case INGRED:
                retCursor = db.query(IngredEntry.TABLE_NAME, null, null,
                        null, null, null, null);
                break;
            default:
                throw new UnsupportedOperationException("default switch");

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        */
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        /*
        final SQLiteDatabase db = mIngredDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        int ingredient;
        switch (match){
            case INGRED:
            ingredient = db.update(IngredEntry.TABLE_NAME,contentValues,null, null);
                break;
            default:
                throw new UnsupportedOperationException("default switch");
        }
       getContext().getContentResolver().notifyChange(uri,null);
       */
        return 0;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        return super.bulkInsert(uri, values);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

}
