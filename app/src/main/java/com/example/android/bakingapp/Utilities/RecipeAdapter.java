package com.example.android.bakingapp.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterVH> {
    private String[] mRecipes = {"Loading Recipes","Please Wait"};
    final private  RecipeOnClickListener mClickListener;
    public RecipeAdapter(RecipeOnClickListener clickListener){
        mClickListener = clickListener;
    }
    public interface RecipeOnClickListener{
        void onClick(int index);
    }
    @Override
    public RecipeAdapterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        int recipeLayout = R.layout.recipe_text_layout;
        LayoutInflater inflater =LayoutInflater.from(context);
         View view = inflater.inflate(recipeLayout,parent, false);
        return new RecipeAdapterVH(view);
    }
    @Override
    public void onBindViewHolder(RecipeAdapterVH holder, int position) {
        String recipeBind = mRecipes[position];
        Log.d("recipeBind", recipeBind);
        holder.mRecipeTV.setText(recipeBind);
    }
    @Override
    public int getItemCount() {
        if(mRecipes==null) return 0;
        return mRecipes.length;
    }

    public class RecipeAdapterVH extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mRecipeTV;
        public RecipeAdapterVH(View view){
            super(view);
            mRecipeTV = view.findViewById(R.id.recipe_text_view);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int itemClicked = getAdapterPosition();
            mClickListener.onClick(itemClicked);
        }
    }
    public void updateRecipes(String[] jsonRecipes){
        mRecipes = jsonRecipes;
        notifyDataSetChanged();
    }


}
