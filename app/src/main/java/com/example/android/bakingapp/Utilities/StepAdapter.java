package com.example.android.bakingapp.Utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterVH> {
    private String[] steps = new String[20];    //array longer than number of steps in json

    final private StepOnClickInterface mStepOnClickListener;
    public StepAdapter(StepOnClickInterface clickInterface){
        steps[0]= "Loading Steps"; steps[1]= "Please wait"; steps[2]="";
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
            if(step!=null){ //recipes have different number of steps. only add text if the string exists
                Log.d("stepBind", step);
                holder.mRecipeTV.setText(step);
            }
    }
    @Override
    public int getItemCount() {
        if(steps==null) return 0;
        return steps.length;
    }
    public class StepAdapterVH extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mRecipeTV;
        public StepAdapterVH(View view){
            super(view);
            mRecipeTV = view.findViewById(R.id.step_text_view);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int stepClicked = getAdapterPosition();
            mStepOnClickListener.onClick(stepClicked);
        }
    }
    public void updateSteps(String[] jsonSteps){
        steps = jsonSteps;
        notifyDataSetChanged();
    }
}

