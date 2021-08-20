package home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import home.category.IOnItemClickCategory;
import com.example.xemxem.MainActivity;

import com.example.xemxem.R;
import home.item.video.Video;
import com.example.xemxem.databinding.FragmentHomeBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import home.category.AdapterCategory;
import home.category.Category;
import watchvideo.FragmentListVideo;

public class FragmentHome extends Fragment implements IHomeVideo {

    FragmentHomeBinding binding;
    AdapterCategory adapterCategory;
    List<Category> categoryList;
    MainActivity mainActivity;
    List<Video> videoGame;
    List<Video> videoMovie;
    List<Video> videoMusic;
    List<Video> videoNew;
    PresenterHomeVideo presenterHomeVideo;
    String urlStringFilm = "https://demo1908811.mockable.io/TrailerFilm";
    String urlStringGame = "https://demo1908811.mockable.io/Game";
    String urlStringMusic = "https://demo1908811.mockable.io/Musics";
    String urlStringNew = "https://demo1908811.mockable.io/News";
    List<String> list;

    public static FragmentHome newInstance() {

        Bundle args = new Bundle();

        FragmentHome fragment = new FragmentHome();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mainActivity= (MainActivity) getActivity();
        list= new ArrayList<>();
        list.add(urlStringGame);
        list.add(urlStringFilm);
        list.add(urlStringMusic);
        list.add(urlStringNew);
        mainActivity= (MainActivity) getActivity();
        adapterCategory= new AdapterCategory(getContext(), mainActivity);
        presenterHomeVideo= new PresenterHomeVideo(this);
        presenterHomeVideo.onGetListVideo(list);
        binding.progressbar.setVisibility(View.VISIBLE);

        adapterCategory.setIOnItemClickVideo(new IOnItemClickCategory() {
            @Override
            public void onTitleCategory(String s,List<Video> videoList) {
                mainActivity.gotoFragmentListVideo(s,videoList);
            }
        });
        return binding.getRoot();
    }


    @Override
    public void onSuccessFul() {
        Toast.makeText(getContext(),"Cập nhật thành công",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMessenger(String mes) {

    }

    @Override
    public void onGetDataVideoHome(List<Category> categories) {

        binding.progressbar.setVisibility(View.GONE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rclvCategoty.setLayoutManager(linearLayoutManager);
        adapterCategory.setData(categories);
        binding.rclvCategoty.setAdapter(adapterCategory);


    }


    
}
