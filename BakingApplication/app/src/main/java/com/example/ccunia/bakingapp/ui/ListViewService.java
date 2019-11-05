package com.example.ccunia.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.ccunia.bakingapp.R;
import com.example.ccunia.bakingapp.data.IngredientList;
import com.example.ccunia.bakingapp.data.SharedPreference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ccunia on 8/10/2018.
 */

/*
the following code is a reference from the link below
https://medium.com/@puruchauhan/android-widget-for-starters-5db14f23009b
*/
public class ListViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        SharedPreference rPref = new SharedPreference();
        ArrayList<IngredientList> list = rPref.getIngredients(this.getApplicationContext());

        return new widgetListView(this.getApplicationContext(), list);
    }

    class widgetListView implements RemoteViewsService.RemoteViewsFactory{

        private ArrayList<IngredientList> ingredient;
        private Context context;

        public widgetListView(Context context, ArrayList<IngredientList> ingredient){
            this.context = context;
            this.ingredient = ingredient;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredient.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_view);

            views.setTextViewText(R.id.ingredient_list_item, ingredient.get(position).toString());

            Intent fillInIntent = new Intent();
            fillInIntent.putExtra("List ingredient",ingredient.get(position));
            //fillInIntent.putExtra("ItemSubTitle",dataList.get(position).subTitle);
            views.setOnClickFillInIntent(R.id.main_list_view, fillInIntent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
