package com.example.xemxem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.xemxem.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.List;

import favorite.FragmentFavorite;
import history.FragmentHistory;
import home.FragmentHome;
import home.item.video.Video;
import profile.FragmentProfile;
import watchvideo.FragmentListVideo;
import watchvideo.FragmentWatchVideo;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    List<Video> list;
    float x1, x2, y1, y2;
    public static final int MIN_DISTANCE=150;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        getFragment(FragmentHome.newInstance());
                        break;
                    case R.id.action_favorite:
                        getFragment(FragmentFavorite.newInstance());
                        break;
                    case R.id.action_profile:
                        getFragment(FragmentProfile.newInstance());
                        break;
                    case R.id.action_history:
                        getFragment(FragmentHistory.newInstance());
                        break;
                    default:
                        getFragment(FragmentHome.newInstance());
                        break;
                }

                return true;
            }
        });
    }

    public void getFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }

    public void gotoFragmentListVideo(String s, List<Video> videoList) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FragmentListVideo fragmentListVideo = FragmentListVideo.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable("listvideo", (Serializable) videoList);
        bundle.putSerializable("title", (Serializable) s);
        fragmentListVideo.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, fragmentListVideo);
        fragmentTransaction.addToBackStack(FragmentListVideo.TAG);
        fragmentTransaction.commit();
    }


    public void gotoFragmentWatchVideo(Video video) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FragmentWatchVideo fragmentWatchVideo = FragmentWatchVideo.newInstance();
        Bundle bundle = new Bundle();
        bundle.putSerializable("video", video);
        fragmentWatchVideo.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, fragmentWatchVideo);
        fragmentTransaction.addToBackStack(FragmentWatchVideo.TAG);
        fragmentTransaction.commit();
    }

    public List<Video> getDataVideoList(List<Video> videoList) {
        list = videoList;
        return list;
    }

//    public boolean onTouchEvent(MotionEvent motionEvent) {
//        switch (motionEvent.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                x1 = motionEvent.getX();
//                //y1 = motionEvent.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                x2 = motionEvent.getX();
//                //y2 = motionEvent.getY();
//                float deltaX= x2-x1;
//                if(Math.abs(deltaX)>MIN_DISTANCE){
//                    Intent intent1= new Intent(MainActivity.this,MainActivity2.class);
//                    startActivity(intent1);
//                }
//                break;
//        }
//        return super.onTouchEvent(motionEvent);
//    }
}
