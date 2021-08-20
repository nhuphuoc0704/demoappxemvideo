package home.category;

import home.item.video.Video;

import java.util.List;

public class Category {
    List<Video> videoList;
    String title;

    public Category(List<Video> videoList, String title) {
        this.videoList = videoList;
        this.title = title;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
