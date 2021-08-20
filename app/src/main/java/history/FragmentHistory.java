package history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xemxem.MainActivity;
import com.example.xemxem.R;
import home.item.video.Video;
import com.example.xemxem.databinding.FragmentHistoryBinding;

import java.util.List;

import favorite.IWatchedVideo;
import favorite.PresenterWatchedVideo;
import watchvideo.AdapterListVideo;
import watchvideo.IOnItemClickVideo;

public class FragmentHistory extends Fragment implements IWatchedVideo {
    public static final String TAG=FragmentHistory.class.getName();
    FragmentHistoryBinding binding;
    AdapterListVideo adapterListVideo;
    PresenterWatchedVideo presenterWatchedVideo;
    List<Video> list;
    public static FragmentHistory newInstance() {

        Bundle args = new Bundle();

        FragmentHistory fragment = new FragmentHistory();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_history,container,false);

        adapterListVideo=new AdapterListVideo(getContext());
        presenterWatchedVideo=new PresenterWatchedVideo(this,getContext());
        presenterWatchedVideo.onShowListVideoWatched();
        adapterListVideo.setiOnItemClickVideo(new IOnItemClickVideo() {
            @Override
            public void onVideoAvatar(int position) {

               ((MainActivity) getActivity()).gotoFragmentWatchVideo(list.get(position));

            }

            @Override
            public void onTitleVideo(int position) {

            }
        });
        binding.btnBackHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onSuccessFull() {
        Toast.makeText(getContext(),"Lịch sử xem các video",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMessenger(String mes) {

    }

    @Override
    public void onVideoWatched(List<Video> videoList) {
        list=videoList;
        adapterListVideo.setData(videoList);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.rclvHistoryWatch.setAdapter(adapterListVideo);
        binding.rclvHistoryWatch.setLayoutManager(linearLayoutManager);
    }
}
