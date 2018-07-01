package com.example.android.bakingapp;

import android.app.Fragment;
import android.os.AsyncTask;

import com.example.android.bakingapp.Utilities.JsonUtility;

public class StepFragment extends Fragment {

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
    }
}
