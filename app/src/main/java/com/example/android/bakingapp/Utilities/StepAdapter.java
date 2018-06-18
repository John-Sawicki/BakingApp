package com.example.android.bakingapp.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterVH> {
    private String[] steps = {"preheat oven", "mix ingrediants", "shale and bake"};
    public StepAdapter(){

    }
    public class StepAdapterVH extends RecyclerView.ViewHolder{
        private final TextView mRecipeTV;
        private StepAdapterVH(View view){
            super(view);
            mRecipeTV = view.findViewById(R.id.recipe_text_view);
        }
    }
    @Override
    public StepAdapter.StepAdapterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(StepAdapterVH holder, int position) {

    }

    @Override
    public void onBindViewHolder(StepAdapterVH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

