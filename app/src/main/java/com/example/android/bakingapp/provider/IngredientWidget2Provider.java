package com.example.android.bakingapp.provider;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidget2Provider extends AppWidgetProvider {






    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId,String ingredients) {
        Log.d("widget", "updateAppWidget"+ingredients);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget2_provider);
        views.setTextViewText(R.id.appwidget_text,ingredients );

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds,String ingredients) {
        Log.d("widget", "updateAppWidgets");

        for(int appWidgetId: appWidgetIds) {
            Log.d("widget", "updateAppWidgets widget # "+appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId,ingredients );
        }
    }
     @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

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

