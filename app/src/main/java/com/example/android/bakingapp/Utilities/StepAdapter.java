package com.example.android.bakingapp.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterVH> {
    private String[] steps = {"preheat oven", "mix ingredients", "shale and bake"};
    private final StepOnClickInterface mStepOnClickListener;
    public StepAdapter(StepOnClickInterface clickInterface){
        mStepOnClickListener = clickInterface;
    }
    public interface StepOnClickInterface{
        void onClick(int index);
    }

    @Override
    public StepAdapter.StepAdapterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int stepLayout = R.layout.step_text_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(stepLayout, parent, false);
        return new StepAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(StepAdapterVH holder, int position) {
            String step = steps[position];
            holder.mRecipeTV.setText(step);
    }

    @Override
    public void onBindViewHolder(StepAdapterVH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }
    @Override
    public int getItemCount() {
        return 0;
    }

    public class StepAdapterVH extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mRecipeTV;
        private StepAdapterVH(View view){
            super(view);
            mRecipeTV = view.findViewById(R.id.recipe_text_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int stepClicked = getAdapterPosition();
            mStepOnClickListener.onClick(stepClicked);
        }
    }
}

