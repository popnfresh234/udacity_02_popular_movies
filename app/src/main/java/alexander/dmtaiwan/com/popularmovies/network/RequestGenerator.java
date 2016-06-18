package alexander.dmtaiwan.com.popularmovies.network;

import android.support.annotation.NonNull;

import alexander.dmtaiwan.com.popularmovies.utilities.Utilities;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Alexander on 6/16/2016.
 */
public class RequestGenerator {

    private static final String baseUrl = "http://api.themoviedb.org/3/movie/popular";
    private static final String PATH_0 = "3";
    private static final String PATH_1 = "movie";
    private static final String PATH_VIDEOS = "videos";
    private static final String PATH_REVIEWS = "reviews";
    private static final String query_param_api_key = "api_key";

    /**
     * Adds default header for all requests
     *
     * @param builder
     */
    private static void addDefaultHeaders(@NonNull Request.Builder builder) {
        builder.header("Accept", "application/json");
    }



    public static Request getMovies() {
        RequestBody formBody = new FormBody.Builder()
                .add("api_key", Utilities.API_KEY)
                .build();
        Request.Builder builder = new Request.Builder().url(baseUrl).post(formBody);

        return builder.build();
    }

    public static Request getVideos(@NonNull String id) {

        HttpUrl url = new HttpUrl.Builder().scheme("http")
                .host("api.themoviedb.org")
                .addPathSegment(PATH_0)
                .addPathSegment(PATH_1)
                .addPathSegment(id)
                .addPathSegment(PATH_VIDEOS)
                .addQueryParameter("api_key", Utilities.API_KEY)
                .build();
        Request.Builder builder = new Request.Builder().url(url);
        return builder.build();
    }

    public static Request getReviews(@NonNull String id) {

        HttpUrl url = new HttpUrl.Builder().scheme("http")
                .host("api.themoviedb.org")
                .addPathSegment(PATH_0)
                .addPathSegment(PATH_1)
                .addPathSegment(id)
                .addPathSegment(PATH_REVIEWS)
                .addQueryParameter("api_key", Utilities.API_KEY)
                .build();
        Request.Builder builder = new Request.Builder().url(url);
        return builder.build();
    }
}
