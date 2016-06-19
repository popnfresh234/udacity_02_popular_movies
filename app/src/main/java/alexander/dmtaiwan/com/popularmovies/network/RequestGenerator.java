package alexander.dmtaiwan.com.popularmovies.network;

import android.support.annotation.NonNull;

import alexander.dmtaiwan.com.popularmovies.utilities.Utilities;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Alexander on 6/16/2016.
 */
public class RequestGenerator {

    private static final String baseUrl = "http://api.themoviedb.org/3/movie/popular";

    private static final String HOST = "api.themoviedb.org";
    private static final String PATH_0 = "3";
    private static final String PATH_1 = "movie";
    private static final String PATH_VIDEOS = "videos";
    private static final String PATH_REVIEWS = "reviews";
    private static final String QUERY_PARAM_API_KEY = "api_key";

    /**
     * Adds default header for all requests
     *
     * @param builder
     */
    private static void addDefaultHeaders(@NonNull Request.Builder builder) {
        builder.header("Accept", "application/json");
    }



    public static Request getMovies(@NonNull String sortOrder) {
        HttpUrl url = new HttpUrl.Builder().scheme("http")
                .host(HOST)
                .addPathSegment(PATH_0)
                .addPathSegment(PATH_1)
                .addPathSegment(sortOrder)
                .addQueryParameter("api_key", Utilities.API_KEY)
                .build();
        Request.Builder builder = new Request.Builder().url(url);
        return builder.build();
    }

    public static Request getVideos(@NonNull String id) {

        HttpUrl url = new HttpUrl.Builder().scheme("http")
                .host(HOST)
                .addPathSegment(PATH_0)
                .addPathSegment(PATH_1)
                .addPathSegment(id)
                .addPathSegment(PATH_VIDEOS)
                .addQueryParameter(QUERY_PARAM_API_KEY, Utilities.API_KEY)
                .build();
        Request.Builder builder = new Request.Builder().url(url);
        return builder.build();
    }

    public static Request getReviews(@NonNull String id) {

        HttpUrl url = new HttpUrl.Builder().scheme("http")
                .host(HOST)
                .addPathSegment(PATH_0)
                .addPathSegment(PATH_1)
                .addPathSegment(id)
                .addPathSegment(PATH_REVIEWS)
                .addQueryParameter(QUERY_PARAM_API_KEY, Utilities.API_KEY)
                .build();
        Request.Builder builder = new Request.Builder().url(url);
        return builder.build();
    }
}
