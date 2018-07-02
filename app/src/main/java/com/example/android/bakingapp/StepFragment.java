package com.example.android.bakingapp;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Utilities.JsonUtility;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import butterknife.BindView;

public class StepFragment extends Fragment {
    @BindView(R.id.step_movie)Movie step_movie;
    @BindView(R.id.no_video_image)ImageView no_video_imge;
    @BindView(R.id.instruction_text)TextView instruction_text;
    Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext= container.getContext();
        Handler mainHandler = new Handler();
        BandwidthMeter bwMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTSF = new AdaptiveTrackSelection.Factory(bwMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTSF);
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

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
