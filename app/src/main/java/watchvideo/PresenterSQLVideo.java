package watchvideo;

import android.content.Context;

import home.item.video.Video;

import java.util.List;

import sql.SQLHelper;

public class PresenterSQLVideo {
    IWatchVideo iWatchVideo;
    SQLHelper sqlHelper;
    Context context;
    List<Video> videoList;
    boolean check=false;
    Video video1;

    public PresenterSQLVideo(IWatchVideo iWatchVideo, Context context) {
        this.iWatchVideo = iWatchVideo;
        this.context = context;
    }

    public void addWatchedVideo(Video video) {
        if(sqlHelper==null) sqlHelper=new SQLHelper(context);
        int id=video.getId();
        videoList=sqlHelper.getAllVideo();
        for(int i=0;i<videoList.size();i++){
            if(id==videoList.get(i).getId()){
                video1=videoList.get(i);
                check=true;
                break;
            }
        }
        if(check) sqlHelper.updateVideoViews(id,video1.getViews()+1);
        else  sqlHelper.insertVideo(video);
        iWatchVideo.onSuccessFull();
    }
    public void updateLikedVideo(Video video){
        if(sqlHelper==null) sqlHelper= new SQLHelper(context);
        int id=video.getId();
        Video video2;
        videoList= sqlHelper.getAllVideo();
        for(int i=0;i<videoList.size();i++){
            video2=videoList.get(i);
            if(id==video2.getId()){
                sqlHelper.updateVideoLikes(id,video2.getLikes()+1);
                iWatchVideo.onSuccessFull();
                break;
            }
        }

    }

}
