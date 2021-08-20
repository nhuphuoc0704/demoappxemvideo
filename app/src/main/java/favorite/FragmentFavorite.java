package favorite;

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
import com.example.xemxem.databinding.FragmentFavoriteBinding;

import java.util.List;

import watchvideo.AdapterListVideo;
import watchvideo.IOnItemClickVideo;

public class FragmentFavorite extends Fragment implements IWatchedVideo{
    FragmentFavoriteBinding binding;
    PresenterWatchedVideo presenterWatchedVideo;
    AdapterListVideo adapterListVideo;
    List<Video> list;
    public static FragmentFavorite newInstance() {

        Bundle args = new Bundle();

        FragmentFavorite fragment = new FragmentFavorite();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_favorite,container,false);
        presenterWatchedVideo= new PresenterWatchedVideo(this,getContext());
        adapterListVideo= new AdapterListVideo(getContext());
        presenterWatchedVideo.onShowListVideoLiked();
        MainActivity mainActivity= (MainActivity) getActivity();
        adapterListVideo.setiOnItemClickVideo(new IOnItemClickVideo() {
            @Override
            public void onVideoAvatar(int position) {
                mainActivity.gotoFragmentWatchVideo(list.get(position));
            }

            @Override
            public void onTitleVideo(int position) {

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onSuccessFull() {
        Toast.makeText(getContext(),"Cập nhật thành công",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onMessenger(String mes) {

    }

    @Override
    public void onVideoWatched(List<Video> videoList) {
        list= videoList;
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        adapterListVideo.setData(videoList);
        binding.rclvListVideoWatched.setLayoutManager(linearLayoutManager);
        binding.rclvListVideoWatched.setAdapter(adapterListVideo);
    }
}
