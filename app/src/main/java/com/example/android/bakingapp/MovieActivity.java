package com.example.android.bakingapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.BindView;

public class MovieActivity extends AppCompatActivity {
    @BindView(R.id.exo_player)SimpleExoPlayerView mSimpleExoPlayerView;
    //SimpleExoPlayerView mSimpleExoPlayerView;
    SimpleExoPlayer mExoPlayer;
    String testUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/590129a5_10-mix-in-melted-chocolate-for-frosting-yellow-cake/10-mix-in-melted-chocolate-for-frosting-yellow-cake.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();    //https://google.github.io/ExoPlayer/guide.html
        TrackSelector trackSelector = new DefaultTrackSelector(     //site has TrackSelector.Factory
                new AdaptiveTrackSelection.Factory(bandwidthMeter));
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        mSimpleExoPlayerView.setPlayer(mExoPlayer);//site
        Uri videoUri = Uri.parse(testUrl);
        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoPlayerVideo");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();//not used in site
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);
        mExoPlayer.prepare(videoSource);

    }
}
