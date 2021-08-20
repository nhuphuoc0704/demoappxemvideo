package home.category;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xemxem.MainActivity;
import com.example.xemxem.R;

import java.util.List;

import home.item.video.AdapterVideoItem;
import home.item.video.Video;
import watchvideo.IOnItemClickVideo;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {
    List<Category> categoryList;
    Context context;
    IOnItemClickCategory iOnItemClickCategory;
    MainActivity mainActivity;
    public void setIOnItemClickVideo(IOnItemClickCategory iOnItemClickCategory){
        this.iOnItemClickCategory = iOnItemClickCategory;
    }

    public AdapterCategory(Context context,MainActivity mainActivity) {
        this.context = context;
        this.mainActivity= mainActivity;
    }
    public void setData(List<Category> categoryList){
        this.categoryList= categoryList;

    }

    @NonNull
    @Override
    public AdapterCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategory.ViewHolder holder, int position) {
            Category category= categoryList.get(position);
            holder.title.setText(category.getTitle());

        LinearLayoutManager manager= new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.recyclerView.setLayoutManager(manager);
        AdapterVideoItem item= new AdapterVideoItem(context);
        item.setData(category.getVideoList());
        holder.recyclerView.setAdapter(item);
        item.setiOnItemClickVideo(new IOnItemClickVideo() {
            @Override
            public void onVideoAvatar(int position) {
                mainActivity.gotoFragmentWatchVideo(category.getVideoList().get(position));
            }

            @Override
            public void onTitleVideo(int position) {
                Video video= category.getVideoList().get(position);
                Dialog dialog= new Dialog(mainActivity);
                dialog.setContentView(R.layout.dialog_video);
                dialog.show();
                VideoView videoView= dialog.findViewById(R.id.vvVideoXemTruoc);
                TextView textView= dialog.findViewById(R.id.tvTitleVideoXemTruoc);
                textView.setText(video.getTitle());
                Uri uri= Uri.parse(video.getUrlLink());
                videoView.setVideoURI(uri);
                videoView.start();
                videoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainActivity.gotoFragmentWatchVideo(video);
                        dialog.dismiss();
                    }
                });
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnItemClickCategory.onTitleCategory(category.getTitle(),category.getVideoList());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tvTitleCategory);
            recyclerView=itemView.findViewById(R.id.rcvlVideo);
        }
    }
}
