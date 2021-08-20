package home.item.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xemxem.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import watchvideo.IOnItemClickVideo;

public class AdapterVideoItem extends RecyclerView.Adapter<AdapterVideoItem.ViewHolder> {
    List<Video> videoList;
    Context context;
    IOnItemClickVideo iOnItemClickVideo;
    public  void setData(List<Video> videoList) {
        this.videoList = videoList;

    }
    public void setiOnItemClickVideo(IOnItemClickVideo iOnItemClickVideo){
        this.iOnItemClickVideo=iOnItemClickVideo;
    }
    public AdapterVideoItem(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterVideoItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVideoItem.ViewHolder holder, int position) {
        Video video= videoList.get(position);
        holder.title.setText(video.getTitle());
        Picasso.with(context).load(video.getAvatar()).into(holder.avatar);
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnItemClickVideo.onVideoAvatar(position);
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnItemClickVideo.onTitleVideo(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView avatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tvTitle);
             avatar=itemView.findViewById(R.id.imgAvatar);
        }
    }
}
