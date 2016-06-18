package alexander.dmtaiwan.com.popularmovies.main;

import java.util.ArrayList;

import alexander.dmtaiwan.com.popularmovies.model.Movie;

/**
 * Created by Alexander on 6/17/2016.
 */
public interface IMainPresenter {
    void fetchData(String sortOrder);

    void onDataReturned(ArrayList<Movie> movieList);

    void onError(int error);
}
