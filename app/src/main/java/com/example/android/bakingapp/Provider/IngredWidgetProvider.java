package com.example.android.bakingapp.Provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.IngredientService;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredWidgetProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                String ingredients, int appWidgetId) {
        Intent intent = new Intent(context, RecipeActivity.class);
        if(ingredients==null) ingredients = "Select a recipe to get a list of ingredients.";
        
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingrediant_widget);
        //CharSequence widgetText = context.getString(R.string.appwidget_text);   //will have ingredients
        Log.d("IngredProvider upWid", ingredients);
        views.setTextViewText(R.id.appwidget_text, ingredients);    //text viewable on the home screen

        //TODO check if there is a way to send an int for the recipe to the widget to go to the RecipeDetail.class
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                        String ingredients, int[] appWidgetIds) {
        Log.d("IngredProvider upWids", ingredients);
        for(int appWidgetId: appWidgetIds){
            updateAppWidget(context, appWidgetManager, ingredients, appWidgetId);
        }

    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        IngredientService.startActionUpdateIngred(context);
    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }
    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

