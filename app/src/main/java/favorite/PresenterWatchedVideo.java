package favorite;

import android.content.Context;

import home.item.video.Video;

import java.util.List;

import sql.SQLHelper;

public class PresenterWatchedVideo {
    IWatchedVideo iWatchedVideo;
    Context context;
    SQLHelper sqlHelper;

    public PresenterWatchedVideo(IWatchedVideo iWatchedVideo, Context context) {
        this.iWatchedVideo = iWatchedVideo;
        this.context = context;
    }
    public void onShowListVideoWatched(){
        if(sqlHelper==null)sqlHelper=new SQLHelper(context);
        List<Video> videoList= sqlHelper.getAllVideo();
        //iWatchedVideo.onSuccessFull();
        iWatchedVideo.onVideoWatched(videoList);
    }
    public void onShowListVideoLiked(){
        if(sqlHelper==null)sqlHelper=new SQLHelper(context);
        List<Video> videoList= sqlHelper.getAllLikedVideo();
        //iWatchedVideo.onSuccessFull();
        iWatchedVideo.onVideoWatched(videoList);
    }
}
