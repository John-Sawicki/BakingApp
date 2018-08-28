package com.example.android.bakingapp.provider;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

//import com.example.android.bakingapp.provider.IngredWidgetProvider;
import com.example.android.bakingapp.provider.IngredContract.IngredEntry;
import static com.example.android.bakingapp.provider.IngredContract.BASE_CONTENT_URI;
import static com.example.android.bakingapp.provider.IngredContract.PATH_INGRED;

public class IngredientService extends IntentService {
    private String ingredients ="Please select a recipe\n to get a list\n of ingredients\n " +
            "350G bittersweet chocolate (60-70% cacao)";
    public IngredientService(){
        super("IntentService");
    }
    public static final String ACTION_UPDATE_INGREDIENTS = "com.example.android.bakingapp.Utilities.action.update_ingredients";
                                                    //package   action      name
    public static void startActionUpdateIngred(Context context){
        Intent intent = new Intent(context, IngredientService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        context.startService(intent);
        Log.d("widget", "startAction");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null){
            final String action = intent.getAction();
            if(ACTION_UPDATE_INGREDIENTS.equals(action)) {//query db to get the ingredients
                Log.d("widget", "onHandeIntent");
                handleActionUpdateIngredients();
            }

        }
    }
    private void handleActionUpdateIngredients(){
        Log.d("widget", "handleAction");
        /*
        Uri INGRED_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGRED).build();
        Cursor cursor = getContentResolver().query(INGRED_URI,
                null, null, null, null);
        String ingredients="";
        if(cursor!=null&&cursor.getCount()>=0){
            Log.d("InService", "handleActionUpdate");
            cursor.moveToFirst();
            int ingredIndex = cursor.getColumnIndex(IngredEntry.COLUMN_INGREDIENTS);
            ingredients = cursor.getString(ingredIndex);
            Log.d("InService handActUp in", ingredients);
            cursor.close();

        }
        */
        if(ingredients==null)ingredients ="Please Select a recipe";
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidget2Provider.class));
        IngredientWidget2Provider.updateAppWidgets(this, appWidgetManager,  appWidgetIds,ingredients );
        //ContentValues contentValues = new ContentValues();
        //getContentResolver().query()

    }
}
