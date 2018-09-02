package com.example.android.bakingapp.utilities;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeDetail;

public class RecipeWidgetVoid extends AppWidgetProvider {
    static void updateIngredWid(Context context, AppWidgetManager appWidgetManager, int widgetId){
        Intent intent = new Intent(context, RecipeDetail.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews view = new  RemoteViews(context.getPackageName(), R.layout.ingrediant_widget);
        view.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        appWidgetManager.updateAppWidget(widgetId, view);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds ) {
        for(int appWidgetId: appWidgetIds){
            updateIngredWid(context, appWidgetManager, appWidgetId);
        }
    }
}
