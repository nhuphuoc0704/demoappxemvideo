package watchvideo;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xemxem.MainActivity;
import com.example.xemxem.R;
import home.item.video.Video;
import com.example.xemxem.databinding.FragmentListvideoBinding;

import java.util.List;

public class FragmentListVideo extends Fragment   {
    public static final String TAG=FragmentListVideo.class.getName();
    FragmentListvideoBinding binding;
    AdapterListVideo adapterListVideo;
    List<Video> videoList;
    MainActivity mainActivity;
    public static FragmentListVideo newInstance() {

        Bundle args = new Bundle();

        FragmentListVideo fragment = new FragmentListVideo();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_listvideo,container,false);
        Bundle bundle= getArguments();
        mainActivity= (MainActivity) getActivity();
        //presenterHomeVideo.onGetListVideo(urlLink);
        videoList= (List<Video>) bundle.get("listvideo");
        String title= (String) bundle.get("title");
        binding.tvTypeVideo.setText(title);
        adapterListVideo=new AdapterListVideo(getContext());
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        adapterListVideo.setData(videoList);
        binding.rclvListVideo.setLayoutManager(linearLayoutManager);
        binding.rclvListVideo.setAdapter(adapterListVideo);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        adapterListVideo.setiOnItemClickVideo(new IOnItemClickVideo() {
            @Override
            public void onVideoAvatar(int position) {
                mainActivity.gotoFragmentWatchVideo(videoList.get(position));
            }

            @Override
            public void onTitleVideo(int position) {


            }
        });
        
        return binding.getRoot();
    }

}
