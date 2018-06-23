package com.example.android.bakingapp.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterVH> {
    String[] mRecipes = {"cake","cookies", "pie" };
    private final RecipeOnClickListener mClickListener;
    public interface RecipeOnClickListener{
        void onClick(String recipe);
    }
    public RecipeAdapter(RecipeOnClickListener clickListener){
            mClickListener = clickListener;
    }
    public class RecipeAdapterVH extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView mRecipeTV;
        private RecipeAdapterVH(View view){
            super(view);
            mRecipeTV = view.findViewById(R.id.recipe_text_view);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int itemClicked = getAdapterPosition();
            String selectedRecipe = mRecipes[itemClicked];
            mClickListener.onClick(selectedRecipe);
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
