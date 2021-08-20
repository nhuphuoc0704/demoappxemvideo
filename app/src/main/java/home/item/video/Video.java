package home.item.video;

import java.io.Serializable;

public class Video implements Serializable {
    int id;
    String title;
    String avatar;
    String urlLink;
    int views;
    int likes;


    public Video(int id, String title, String avatar, String urlLink, int views, int likes) {
        this.id = id;
        this.title = title;
        this.avatar = avatar;
        this.urlLink = urlLink;
        this.views = views;
        this.likes = likes;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }


}
