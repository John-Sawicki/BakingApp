package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.utilities.JsonUtility;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
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
    @BindView(R.id.step_movie)SimpleExoPlayerView mSimpleExoPlayerView;
    @BindView(R.id.no_video_text)TextView no_video_text;
    @BindView(R.id.instruction_text)TextView instruction_text;
    Context mContext;
    SimpleExoPlayer mExoPlayer;
    private long movieTime= 0;
    private Unbinder mUnbinder;
    private int mRecipeIndex=0; //ex brownies, this stays the same in this activity
    private int mStepIndex=0;  //ex step 2; increment to go to the next step by replacing a fragment
    private int playbackState = Player.STATE_READY;  //int 3 represents modifier of the exoplayer
    private boolean playMovie = true;
    private int[] stepFragRecipeStepValues ={0,0};
    private String testUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/590129a5_10-mix-in-melted-chocolate-for-frosting-yellow-cake/10-mix-in-melted-chocolate-for-frosting-yellow-cake.mp4";
    private boolean phoneLandscape = false, valuesSaved;
    private final static String RECIPE_KEY = "saveRecipeKey", STEP_KEY = "saveStepKey", VALUES_SAVED = "valuesSaved",
            TIME_KEY ="timeKey", KEY_VIDEO_STATE="KEY_VIDEO_STATE";
    public StepFragment( ){
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_fragment, container, false);
        mContext = getActivity().getApplicationContext();
        mUnbinder= ButterKnife.bind(this,view);
        no_video_text.setVisibility(View.VISIBLE);
        no_video_text.setText(R.string.check_for_video);
        no_video_text.setText(R.string.check_for_video);
        mSimpleExoPlayerView.setVisibility(View.INVISIBLE);
        instruction_text.setText(R.string.loading_message);
        Log.d("RSD StepFrag onC Index",valuesSaved+" "+stepFragRecipeStepValues[0]+" "+stepFragRecipeStepValues[1]+" time "+movieTime);
        return view;
    }
    public class getInstructionsLong extends AsyncTask<int[], Void, String[]> {
        String StepLongMovieUrl[] = new String[2];  //first element is the long description, second is the movie url
        @Override
        protected String[] doInBackground(int[]... integers) {
            try{
                stepFragRecipeStepValues = integers[0];
                Log.d("RSD StepFrag doIB str ","Index "+stepFragRecipeStepValues[0]+" "+stepFragRecipeStepValues[1]);
                mRecipeIndex = stepFragRecipeStepValues[0];
                mStepIndex= stepFragRecipeStepValues[1];
                String jsonStringFromWeb = JsonUtility.getResponseFromSite(JsonUtility.JsonUrl);
                StepLongMovieUrl = JsonUtility.getStepsLong(jsonStringFromWeb, mRecipeIndex, mStepIndex, mContext);
                Log.d("RSD StepFrag doIB end","Index"+StepLongMovieUrl[0]+" "+StepLongMovieUrl[1]);
                return StepLongMovieUrl;
            }catch (Exception e){
                e.printStackTrace();
                Log.d("RSD StepFrag doInback","return null");
                return null;
            }
        }
        @Override
        protected void onPostExecute(String[] strings) {//first element is long description second is movie url
            Log.d("RSD StepFrag onPE Index",strings[0]+" "+strings[1] );
            if(strings[0]!=null){
                Log.d("RSD StepFrag onPost",strings[0] );
                instruction_text.setText(strings[0]);
            }else{
                Log.d("RSD StepFrag onPost", "no instructions");
                instruction_text.setText(R.string.no_instruction_text);
            }
            if(strings[1]!=null)Log.d("StepFragment Post 1  ",strings[1] );
            if(strings[1]==null || strings[1]==""||strings[1]=="empty"|| strings[1].length()==0){    //valid urls start with http
                Log.d("RSD StepFrag noVideo", "no video");
                no_video_text.setVisibility(View.VISIBLE);
                no_video_text.setText(R.string.no_video_text);
                mSimpleExoPlayerView.setVisibility(View.INVISIBLE);
            }else{
                Log.d("RSD StepFrag Video", strings[1]);
                no_video_text.setVisibility(View.INVISIBLE);
                mSimpleExoPlayerView.setVisibility(View.VISIBLE);
                try {
                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                    mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
                    Uri videoURI = Uri.parse(strings[1]);
                    DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                    MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoURI);
                    mSimpleExoPlayerView.setPlayer(mExoPlayer);
                    mExoPlayer.prepare(mediaSource);
                    if(playbackState !=Player.STATE_READY) {
                        playMovie = false;
                        Log.d("RSD StepFrag Video", "state "+playbackState+" play when ready "+playMovie);
                    }
                    mExoPlayer.setPlayWhenReady(playMovie);
                    Log.d("RSD StepFrag Video", "time post execute "+movieTime+" play when ready "+playMovie);
                    mExoPlayer.seekTo(movieTime);//when the screen is rotated, it will move to the time saved on rotation

                }catch (Exception e){
                    Log.e("RSD StepFrag catch", " exoplayer error " + e.toString());
                }
            }
        }
    }
    @Override
    public void onDestroyView() {
        //Log.d("StepFragment video", "onDestroy");
        if(mExoPlayer!=null){
            mSimpleExoPlayerView.getPlayer();
            mSimpleExoPlayerView.getPlayer().stop();
            mSimpleExoPlayerView.getPlayer().release();
            mExoPlayer.release();
            mSimpleExoPlayerView= null;
            mExoPlayer= null;
        }
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_KEY, stepFragRecipeStepValues[0]);
        outState.putInt(STEP_KEY, stepFragRecipeStepValues[1]);
        outState.putBoolean(VALUES_SAVED, true);

        Log.d("RSD StepFrag onSv Index",stepFragRecipeStepValues[0]+" "+stepFragRecipeStepValues[1]);

    }
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Bundle bundle = getArguments();
        stepFragRecipeStepValues[0] = bundle.getInt("recipeIndex",0);
        stepFragRecipeStepValues[1]= bundle.getInt("stepIndex",0);
        movieTime = bundle.getLong(TIME_KEY, 0);
        playbackState= bundle.getInt(KEY_VIDEO_STATE, 0);
        Log.d("RSD StepFrag sIS Index",valuesSaved+" "+stepFragRecipeStepValues[0]+" "+stepFragRecipeStepValues[1]+" time "+movieTime+" state "+playbackState);
        if(savedInstanceState!=null){
            stepFragRecipeStepValues[0]= savedInstanceState.getInt(RECIPE_KEY);
            stepFragRecipeStepValues[1]= savedInstanceState.getInt(STEP_KEY);
            valuesSaved =savedInstanceState.getBoolean(VALUES_SAVED);
            Log.d("RSD StepFrag oVSR Index",valuesSaved+" "+stepFragRecipeStepValues[0]+" "+stepFragRecipeStepValues[1] );
        }
        if(savedInstanceState==null){
            Log.d("RSD StepFrag execute", "saved ==nul");
            new getInstructionsLong().execute(stepFragRecipeStepValues);    //first element is the long description, second is the movie url
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer!=null){
            movieTime = mExoPlayer.getContentPosition();
            playbackState = mExoPlayer.getPlaybackState();
            mExoPlayer.setPlayWhenReady(false);

        }
        Bundle videoInfo = new Bundle();
        videoInfo.putLong(TIME_KEY, movieTime);
        videoInfo.putInt(KEY_VIDEO_STATE,playbackState );
        Intent intent = getActivity().getIntent();
        intent.putExtras(videoInfo);
        //intent.putExtras(videoInfo);    //pass to activity before saveInstance
        Log.d("RSD StepFrag video", "time "+movieTime+" state "+playbackState);
    }
}