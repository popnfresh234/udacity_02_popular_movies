package alexander.dmtaiwan.com.popularmovies.main;

import java.util.ArrayList;

import alexander.dmtaiwan.com.popularmovies.model.Movie;

/**
 * Created by Alexander on 6/17/2016.
 */
public class MainPresenter implements IMainPresenter {
    private IMainView mainView;
    private IMainInteractor mainInteractor;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
        mainInteractor = new MainInteractor(this);
    }

    @Override
    public void fetchData(String sortOrder) {
        mainInteractor.fetchData(sortOrder);
    }

    @Override
    public void onDataReturned(ArrayList<Movie> movieList) {
        mainView.onDataReturned(movieList);
    }

    @Override
    public void onError(int error) {
        mainView.onError(error);
    }


}
