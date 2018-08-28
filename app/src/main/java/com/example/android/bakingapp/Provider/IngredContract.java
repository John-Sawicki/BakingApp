package com.example.android.bakingapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class IngredContract {
    public static final String AUTHORITY = "com.example.android.bakingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    public static final String PATH_INGRED = "ingred";
    public static final long INVALID_INGRED_ID = -1;
    public static final class IngredEntry implements BaseColumns{
        public static final Uri CONTENT_URI= BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGRED).build();
        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_INGREDIENTS = "ingredients";
    }
}
