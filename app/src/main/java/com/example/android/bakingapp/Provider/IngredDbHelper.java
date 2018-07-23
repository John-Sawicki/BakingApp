package com.example.android.bakingapp.Provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.bakingapp.Provider.IngredContract.IngredEntry;

public class IngredDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="ingredients.db";
    private static final int DATABASE_VERSION= 1;
    public IngredDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_PLANTS_TABLE = "CREATE TABLE "+IngredEntry.TABLE_NAME+" ("+
                IngredEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                IngredEntry.COLUMN_INGREDIENTS+" STRING NOT NULL)";
        sqLiteDatabase.execSQL(SQL_CREATE_PLANTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IngredEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
