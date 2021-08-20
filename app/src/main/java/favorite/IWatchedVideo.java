package favorite;

import home.item.video.Video;

import java.util.List;

public interface IWatchedVideo {
    void onSuccessFull();
    void onMessenger(String mes);
    void onVideoWatched(List<Video> videoList);
}
