package com.example.android.bakingapp;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Utilities.JsonUtility;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepFragment extends Fragment {
    @BindView(R.id.step_movie)SimpleExoPlayerView step_movie;
    @BindView(R.id.no_video_text)TextView no_video_text;
    @BindView(R.id.instruction_text)TextView instruction_text;
    //SimpleExoPlayer step_movie;
   // ImageView no_video_image;
   // TextView instruction_text;
    Context mContext;
    SimpleExoPlayer mExoPlayer;
    private Unbinder mUnbinder;
    int mRecipeIndex=0; //ex brownies, this stays the same in this activity
    int mStepIndex=0;  //ex step 2; increment to go to the next step by replacing a fragment
    int[] stepFragRecipeStepValues ={0,0};
    String testUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/590129a5_10-mix-in-melted-chocolate-for-frosting-yellow-cake/10-mix-in-melted-chocolate-for-frosting-yellow-cake.mp4";
    public StepFragment( ){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_fragment, container, false);
        mContext= container.getContext();
        //step_movie = view.findViewById(R.id.step_movie);
        //no_video_image = view.findViewById(R.id.no_video_image);
        //instruction_text = view.findViewById(R.id.instruction_text);
        mUnbinder= ButterKnife.bind(this,view);

        new getInstructionsLong().execute(stepFragRecipeStepValues);    //first element is the long description, second is the movie url

        return view;
    }

    public class getInstructionsLong extends AsyncTask<int[], Void, String[]> {
        String StepLongMovieUrl[] = new String[2];  //first element is the long description, second is the movie url

        @Override
        protected String[] doInBackground(int[]... integers) {
            try{
                stepFragRecipeStepValues = integers[0];
                mRecipeIndex = stepFragRecipeStepValues[0];
                mStepIndex= stepFragRecipeStepValues[1];
                Log.d("stepBackground 0  ",mRecipeIndex+"");Log.d("stepBackground 1  ",mStepIndex+"");
                String jsonStringFromWeb = JsonUtility.getResponseFromSite(JsonUtility.JsonUrl);
                StepLongMovieUrl = JsonUtility.getStepsLong(jsonStringFromWeb, mRecipeIndex, mStepIndex);
                return StepLongMovieUrl;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String[] strings) {//first element is long description second is movie url
            if(strings[0]!=null){
                Log.d("stepFragmentPost 0  ",strings[0] );
                Log.d("stepFragmentPost 1  ",strings[1] );
                instruction_text.setText(strings[0]);
            }else{
                instruction_text.setText("There are no instructions for this step.");
            }
            if(strings[1]!=null){
                no_video_text.setVisibility(View.INVISIBLE);
                step_movie.setVisibility(View.VISIBLE);
                try {
                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                    mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
                    //Uri videoURI = Uri.parse(testUrl);
                    Uri videoURI = Uri.parse(strings[1]);
                    DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                    MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoURI);
                    step_movie.setPlayer(mExoPlayer);
                    mExoPlayer.prepare(mediaSource);

                    mExoPlayer.setPlayWhenReady(true);
                }catch (Exception e){
                    Log.e("MainActivity", " exoplayer error " + e.toString());
                }
            }else{
                no_video_text.setVisibility(View.VISIBLE);
                step_movie.setVisibility(View.INVISIBLE);
            }
        }
    }
    public void setDescAndURL(int[] intDescAndUrl){
        mRecipeIndex = intDescAndUrl[0];
        mStepIndex = intDescAndUrl[1];
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
