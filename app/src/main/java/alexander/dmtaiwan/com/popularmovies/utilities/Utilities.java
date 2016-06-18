package alexander.dmtaiwan.com.popularmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by lenovo on 6/16/2016.
 */
public class Utilities {
    public static final String EXTRA_MOVIE = "com.dmtaiwan.alexander.extra.movie";
    public static final String API_KEY = "02134457e3479763fd29902a1e1235c3";

    public static final int REVIEW_CODE = 0;
    public static final int VIDEO_CODE = 1;

    public static final String URL_POPULAR = "http://api.themoviedb.org/3/movie/popular";
    public static final String URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated";
    public static final String URL_REVIEWS = "http://api.themoviedb.org/3/movie/id/reviews";
    public static final String URL_VIDEOS = "http://api.themoviedb.org/3/movie/id/videos";

    public static final int ERROR_NETWORK_UNAVAILABLE = 0;
    public static final int ERROR_NETWORK_FAILED = 1;
    public static final int ERROR_JSON = 2;

    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
