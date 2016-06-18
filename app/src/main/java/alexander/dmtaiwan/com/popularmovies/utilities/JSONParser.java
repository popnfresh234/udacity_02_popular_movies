package alexander.dmtaiwan.com.popularmovies.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Movie> parse(String json) throws JSONException{
        JSONObject object = new JSONObject(json);
        JSONArray results = object.getJSONArray(RESULTS);
        List<Movie> movieList = new ArrayList<>();
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

    public static List<Video> parseVidoes(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONArray results = object.getJSONArray(RESULTS);
        List<Video> videos = new ArrayList<>();

        if (results.length() > 0) {
            for(int i = 0; i < results.length(); i ++) {
                JSONObject jsonVideo = results.getJSONObject(i);
                Video video = new Video();
                video.setKey(jsonVideo.getString(KEY));
                videos.add(video);
            }
        }
        return videos;
    }

    public static List<Review> parseReviews(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONArray results = object.getJSONArray(RESULTS);
        List<Review> reviews = new ArrayList<>();

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
