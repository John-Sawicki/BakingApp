package com.example.android.bakingapp.Utilities;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.android.bakingapp.IngredWidgetProvider;
import com.example.android.bakingapp.Utilities.IngredContract.IngredEntry;
import static com.example.android.bakingapp.Utilities.IngredContract.BASE_CONTENT_URI;
import static com.example.android.bakingapp.Utilities.IngredContract.PATH_INGRED;
public class IngredientService extends IntentService {
    public IngredientService(){
        super("IntentService");
    }
    public static final String UPDATE_INGREDIENTS = "com.example.android.bakingapp.Utilities.action.update_ingredients";
                                                    //package   action      name
    public static void startActionUpdateIngred(Context context){
        Intent intent = new Intent(context, IngredientService.class);
        intent.setAction(UPDATE_INGREDIENTS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null){
            final String action = intent.getAction();
            if(UPDATE_INGREDIENTS.equals(action))   //query db to get the ingredients
                handleActionUpdateIngredients();
        }
    }
    private void handleActionUpdateIngredients(){
        Uri INGRED_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGRED).build();
        Cursor cursor = getContentResolver().query(INGRED_URI,
                null, null, null, null);
        String ingredients="";
        if(cursor!=null&&cursor.getCount()>=0){
            cursor.moveToFirst();
            int ingredIndex = cursor.getColumnIndex(IngredEntry.COLUMN_INGREDIENTS);
            ingredients = cursor.getString(ingredIndex);
            cursor.close();

        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetId = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredWidgetProvider.class));
        IngredWidgetProvider.updateAppWidgets(this,appWidgetManager, ingredients, appWidgetId );
        //ContentValues contentValues = new ContentValues();
        //getContentResolver().query()

    }
}
