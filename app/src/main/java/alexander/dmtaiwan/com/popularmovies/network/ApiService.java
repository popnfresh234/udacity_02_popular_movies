package alexander.dmtaiwan.com.popularmovies.network;

import java.util.List;

import alexander.dmtaiwan.com.popularmovies.model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lenovo on 6/16/2016.
 */
public interface ApiService {
    @GET("3/movie/popular?{key}")
    Call<List<Movie>> listMovies(@Path("key") String key);
}
