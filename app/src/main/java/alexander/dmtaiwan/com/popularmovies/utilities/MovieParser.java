package alexander.dmtaiwan.com.popularmovies.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import alexander.dmtaiwan.com.popularmovies.model.Movie;

/**
 * Created by Alexander on 6/17/2016.
 */
public class MovieParser {
    private static final String RESULTS = "results";
    private static final String POSTER_PATH = "poster_path";
    private static final String ID = "id";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";

    public static List<Movie> parse(String json) throws JSONException{
        JSONObject object = new JSONObject(json);
        JSONArray results = object.getJSONArray(RESULTS);
        List<Movie> movieList = new ArrayList<>();
        if (results.length() > 0) {
            for(int i =0; i < results.length(); i++) {
                JSONObject jsonMovie = results.getJSONObject(i);
                Movie movie = new Movie();
                movie.setPoster_path(jsonMovie.getString(POSTER_PATH));
                movie.setId(jsonMovie.getString(ID));
                movie.setVote_average(jsonMovie.getString(VOTE_AVERAGE));
                movie.setOriginal_title(jsonMovie.getString(ORIGINAL_TITLE));
                movie.setOverview(jsonMovie.getString(OVERVIEW));
                movieList.add(movie);
            }
        }
        return movieList;
    }
}
