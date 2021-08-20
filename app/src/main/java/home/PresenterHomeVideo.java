package home;

import android.os.AsyncTask;

import home.item.video.Video;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import home.category.Category;

public class PresenterHomeVideo {
    IHomeVideo iHomeVideo;
    List<Category> categoryList;
    List<Video> videoListGame;
    List<Video> videoListMovie;
    List<Video> videoListMusic;
    List<Video> videoListNew;
    List<String> linkURL;

    public PresenterHomeVideo(IHomeVideo iHomeVideo) {
        this.iHomeVideo = iHomeVideo;
    }

    public void onGetListVideo(List<String> urlLink) {
        linkURL = urlLink;
        categoryList = new ArrayList<>();
        videoListGame = new ArrayList<>();
        videoListMovie = new ArrayList<>();
        videoListNew = new ArrayList<>();
        videoListMusic = new ArrayList<>();
        new GetData().execute();


    }

    class GetData extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected void onPostExecute(List<String> s) {
            super.onPostExecute(s);
            for (int j = 0; j < s.size(); j++) {
                String link = s.get(j);
                try {
                    JSONArray array = new JSONArray(link);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String avatar = jsonObject.getString("avatar");
                        String title = jsonObject.getString("title");
                        int likes = jsonObject.getInt("likes");
                        String urlLink = jsonObject.getString("urlLink");
                        int views = jsonObject.getInt("views");
                        Video video = new Video(id, title, avatar, urlLink, views, likes);
                        if (j == 0) videoListGame.add(video);
                        else if (j == 1) videoListMusic.add(video);
                        else if (j == 2) videoListMovie.add(video);
                        else videoListNew.add(video);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            categoryList.add(new Category(videoListGame, "Games"));
            categoryList.add(new Category(videoListMusic, "Musics"));
            categoryList.add(new Category(videoListMovie, "Moives"));
            categoryList.add(new Category(videoListNew, "News"));
            iHomeVideo.onGetDataVideoHome(categoryList);


        }


        @Override
        protected List<String> doInBackground(Void... voids) {
            List<String> s = new ArrayList<>();
            for (int i = 0; i < linkURL.size(); i++) {
                String result = "";
                try {
                    String link1 = linkURL.get(i);
                    URL url = new URL(link1);
                    URLConnection urlConnection = url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    int byteCharacter;
                    while ((byteCharacter = is.read()) != -1) {
                        result += (char) byteCharacter;
                    }

                    s.add(new String(result));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return s;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


    }
}
