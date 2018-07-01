package com.example.android.bakingapp;

import android.app.Fragment;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Utilities.JsonUtility;

import butterknife.BindView;

public class StepFragment extends Fragment {
    @BindView(R.id.step_movie)Movie step_movie;
    @BindView(R.id.no_video_image)ImageView no_video_imge;
    @BindView(R.id.instruction_text)TextView instruction_text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.step_fragment, container, false);
    }

    public class getInstructionsLong extends AsyncTask<Integer, Void, String[]> {
        String movieURLStepLong[] = new String[2];  //first element is the long description, second is the movie url

        @Override
        protected String[] doInBackground(Integer... integers) {
            try{
                int step = integers[0];
                String jsonStringFromWeb = JsonUtility.getResponseFromSite(JsonUtility.JsonUrl);
                movieURLStepLong = JsonUtility.getStepsLong(jsonStringFromWeb, 0,step) ;
                return  movieURLStepLong;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] strings) {//first element is long description second is movie url
            if(strings[0]!=null){
                no_video_imge.setVisibility(View.INVISIBLE);
                instruction_text.setText(strings[0]);
            }
            if(strings[1]!=null){

            }else{

                no_video_imge.setVisibility(View.VISIBLE);


            }

        }
    }
}
