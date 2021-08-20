package watchvideo;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.xemxem.R;
import home.item.video.Video;
import com.example.xemxem.databinding.FragmentWatchvideoBinding;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import static com.google.android.gms.ads.AdSize.BANNER;

public class FragmentWatchVideo extends Fragment  implements IWatchVideo{
    public static final String TAG=FragmentWatchVideo.class.getName();
    FragmentWatchvideoBinding binding;
    MediaController mediaController;
    int check;
    PresenterSQLVideo presenterSQLVideo;
    ImageView btFullScreen;
    SimpleExoPlayer simpleExoPlayer;
    boolean flag= false;
    public static FragmentWatchVideo newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentWatchVideo fragment = new FragmentWatchVideo();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_watchvideo,container,false);
        Bundle bundle= getArguments();
        Video video= (Video) bundle.get("video");
        Uri uri= Uri.parse(video.getUrlLink());

        btFullScreen= binding.pvVideo.findViewById(R.id.bt_fullscreen);
        //getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        exoPlayer(uri);


//        binding.vvVideo.setVideoURI(uri);
//        mediaController= new MediaController(getContext());
//        mediaController.setMediaPlayer(binding.vvVideo);
//        binding.vvVideo.setMediaController(mediaController);
//        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
//
//            }
//        });

//        AdRequest adRequest= new AdRequest.Builder().build();
//        binding.adBanner.loadAd(adRequest);
//        binding.vvVideo.start();
//        binding.vvVideo.requestFocus();
//        AdSize  adSize= new AdSize(300,50);
//        binding.adBanner.setAdUnitId("ca-app-pub-7338955687239027/9207035242");
//        binding.adBanner.setAdSizes(adSize);
//        AdManagerAdRequest adRequest=new AdManagerAdRequest.Builder().build();
//        binding.adBanner.loadAd(adRequest);

        presenterSQLVideo= new PresenterSQLVideo(this,getContext());
        presenterSQLVideo.addWatchedVideo(video);
        check=0;

        binding.btnBackListVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkLiked=binding.tvLikeWatchVideo.getText().toString();
                if(checkLiked.contains("Liked"))
                    presenterSQLVideo.updateLikedVideo(video);

                getFragmentManager().popBackStack();
            }
        });

        binding.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check++;
                if(check%2!=0)
                binding.tvLikeWatchVideo.setText("Liked");
                else binding.tvLikeWatchVideo.setText(null);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onSuccessFull() {
        Toast.makeText(getContext(),"Video đã được thêm thành công",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMessenger(String mes) {

    }

    @Override
    public void onPause() {
        super.onPause();
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    public void onStart() {
        super.onStart();
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();
    }
    public void exoPlayer(Uri uri){
        LoadControl loadControl= new DefaultLoadControl();
        BandwidthMeter bandwidthMeter= new DefaultBandwidthMeter();
        TrackSelector trackSelector= new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("DevproAD42");
        ExtractorsFactory extractorsFactory= new DefaultExtractorsFactory();
        MediaSource mediaSource= new ExtractorMediaSource(uri,factory,extractorsFactory,null,null);
        binding.pvVideo.setPlayer(simpleExoPlayer);
        binding.pvVideo.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if(playbackState== Player.STATE_BUFFERING)
                    binding.progressbar.setVisibility(View.VISIBLE);
                else if(playbackState==Player.STATE_READY)
                    binding.progressbar.setVisibility(View.GONE);


            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        btFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){

                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreen));
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag= false;

                } else{
                    btFullScreen.setImageDrawable(getResources().getDrawable(R.drawable.ic_fullscreenexit));
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    flag= true;
                }

            }
        });
    }

}
