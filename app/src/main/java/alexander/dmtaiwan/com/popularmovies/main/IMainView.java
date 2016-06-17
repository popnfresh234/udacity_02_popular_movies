package alexander.dmtaiwan.com.popularmovies.main;

import java.util.List;

import alexander.dmtaiwan.com.popularmovies.model.Movie;

/**
 * Created by Alexander on 6/17/2016.
 */
public interface IMainView {
    void onDataReturned(List<Movie> movies);

    void onError(int error);
}
