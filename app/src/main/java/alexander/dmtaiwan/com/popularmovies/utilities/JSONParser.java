package alexander.dmtaiwan.com.popularmovies.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import alexander.dmtaiwan.com.popularmovies.model.Movie;
import alexander.dmtaiwan.com.popularmovies.model.Review;
import alexander.dmtaiwan.com.popularmovies.model.Video;

/**
 * Created by Alexander on 6/17/2016.
 */
public class JSONParser {


    //Movie
    private static final String RESULTS = "results";
    private static final String POSTER_PATH = "poster_path";
    private static final String ID = "id";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String RELEASE_DATE = "release_date";
    private static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w185";
    private static final String BASE_URL_BACKDROP_IMAGE = "http://image.tmdb.org/t/p/w500";

    //Reviews
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";

    //Videos
    private static final String KEY = "key";
    private static final String NAME = "name";
    private static final String YOUTUBE_THUMB_URL_START = "http://img.youtube.com/vi/";
    private static final String YOUTUBE_THUMB_URL_END = "/0.jpg";

    public static ArrayList<Movie> parse(String json) throws JSONException{
        JSONObject object = new JSONObject(json);
        JSONArray results = object.getJSONArray(RESULTS);
        ArrayList<Movie> movieList = new ArrayList<>();
        if (results.length() > 0) {
            for(int i =0; i < results.length(); i++) {
                JSONObject jsonMovie = results.getJSONObject(i);
                Movie movie = new Movie();
                movie.setPoster_path(BASE_URL_IMAGE + jsonMovie.getString(POSTER_PATH));
                movie.setId(jsonMovie.getString(ID));
                movie.setVote_average(jsonMovie.getString(VOTE_AVERAGE));
                movie.setOriginal_title(jsonMovie.getString(ORIGINAL_TITLE));
                movie.setOverview(jsonMovie.getString(OVERVIEW));
                movie.setBackdrop_path(BASE_URL_BACKDROP_IMAGE + jsonMovie.getString(BACKDROP_PATH));
                movie.setRelease_date(jsonMovie.getString(RELEASE_DATE));
                movieList.add(movie);
            }
        }
        return movieList;
    }

    public static ArrayList<Video> parseVidoes(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONArray results = object.getJSONArray(RESULTS);
        ArrayList<Video> videos = new ArrayList<>();

        if (results.length() > 0) {
            for(int i = 0; i < results.length(); i ++) {
                JSONObject jsonVideo = results.getJSONObject(i);
                Video video = new Video();
                video.setName(jsonVideo.getString(NAME));
                video.setKey(jsonVideo.getString(KEY));
                video.setYoutube_thumb(YOUTUBE_THUMB_URL_START + jsonVideo.getString(KEY) + YOUTUBE_THUMB_URL_END);
                videos.add(video);
            }
        }
        return videos;
    }

    public static ArrayList<Review> parseReviews(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONArray results = object.getJSONArray(RESULTS);
        ArrayList<Review> reviews = new ArrayList<>();

        if (results.length() > 0) {
            for(int i = 0; i < results.length(); i ++) {
                JSONObject jsonReview = results.getJSONObject(i);
                Review review = new Review();
                review.setAuthor(jsonReview.getString(AUTHOR));
                review.setContent(jsonReview.getString(CONTENT));
                reviews.add(review);
            }
        }
        return reviews;
    }
}
