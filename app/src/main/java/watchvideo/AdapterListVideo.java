package watchvideo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xemxem.R;
import home.item.video.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListVideo extends RecyclerView.Adapter<AdapterListVideo.ViewHolder> {
    List<Video> videoList;
    Context context;
    IOnItemClickVideo iOnItemClickVideo;

    public void setData( List<Video> videoList){
        this.videoList=videoList;
    }
    public void setiOnItemClickVideo(IOnItemClickVideo iOnItemClickVideo){
        this.iOnItemClickVideo=iOnItemClickVideo;
    }


    public AdapterListVideo(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_listvideo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video= videoList.get(position);
        holder.tvViewed.setText(video.getViews()+"");
        holder.tvLiked.setText(video.getLikes()+"");
        holder.tvTiltleVideo.setText(video.getTitle());
        Picasso.with(context).load(video.getAvatar()).into(holder.avatar);
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnItemClickVideo.onVideoAvatar(position);

            }
        });
        holder.tvTiltleVideo.setOnClickListener(new View.OnClickListener() {
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
        TextView tvTiltleVideo;
        ImageView avatar;
        TextView tvViewed;
        TextView tvLiked;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTiltleVideo=itemView.findViewById(R.id.tvTitleVideo);
            avatar=itemView.findViewById(R.id.imgAvatarVideo);
            tvViewed= itemView.findViewById(R.id.tvViewed);
            tvLiked= itemView.findViewById(R.id.tvLiked);
        }
    }
}
