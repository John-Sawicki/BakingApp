package com.example.android.bakingapp.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterVH> {
    String[] mRecipes = {"cake","cookies", "pie" };
    public RecipeAdapter(){

    }
    public class RecipeAdapterVH extends RecyclerView.ViewHolder{
        private final TextView mRecipeTV;
        private RecipeAdapterVH(View view){
            super(view);
            mRecipeTV = view.findViewById(R.id.recipe_text_view);
        }
    }
    @Override
    public RecipeAdapterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecipeAdapterVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
