package alexander.dmtaiwan.com.popularmovies.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.model.Movie;
import alexander.dmtaiwan.com.popularmovies.utilities.Utilities;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 6/16/2016.
 */
public class MainFragment extends Fragment implements IMainView , ImageAdapter.AdapterListener{

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.gridview)
    GridView mGridView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private List<Movie> mMovies = new ArrayList<Movie>();
    private ImageAdapter mAdapter;
    private MovieListener listener;

    public static MainFragment newInstance(MovieListener listener) {
        MainFragment fragment = new MainFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        if (savedInstanceState != null) {
            mMovies = savedInstanceState.getParcelableArrayList("TEST");
        }

        //Setup the adapter
        mAdapter = new ImageAdapter(mMovies, getActivity(), this);
        mGridView.setAdapter(mAdapter);

        //Create presenter and fetch data if network is available
        //If no network, display error
        MainPresenter presenter = new MainPresenter(this);
        if (Utilities.isNetworkAvailable(getActivity())) {
            if (savedInstanceState == null) {
                presenter.fetchData("test");
            }

        }else onError(Utilities.ERROR_NETWORK_UNAVAILABLE);


        return rootView;
    }


    @Override
    public void onDataReturned(final List<Movie> movies) {
        //Set data
        //Need to run on UI thread since OkHTTP is running in background thread
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMovies = movies;
                    mAdapter.updateData(movies);
                }
            });
        }
    }

    @Override
    public void onError(int error) {
        Snackbar snackbar;
        switch (error) {
            case Utilities.ERROR_NETWORK_UNAVAILABLE:
                snackbar = Snackbar.make(mCoordinatorLayout, getString(R.string.error_network_unavailable), Snackbar.LENGTH_LONG);
                break;
            case Utilities.ERROR_NETWORK_FAILED:
                snackbar = Snackbar.make(mCoordinatorLayout, getString(R.string.error_network_failed), Snackbar.LENGTH_LONG);
                break;
            case Utilities.ERROR_JSON:
                snackbar = Snackbar.make(mCoordinatorLayout, getString(R.string.error_json), Snackbar.LENGTH_LONG);
                break;
            default:
                snackbar = Snackbar.make(mCoordinatorLayout, "WEEEEEEEEEEEEE", Snackbar.LENGTH_LONG);
                break;
        }
        snackbar.show();
    }

    @Override
    public void onItemClicked(Movie movie) {
        //Handle clicks from the Adapter
        listener.onItemSelected(movie);
    }

    //Interface for MainActivity to implement
    public interface MovieListener {
        void onItemSelected(Movie movie);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Movie> movies = new ArrayList<>();
        movies.addAll(mMovies);

        outState.putParcelableArrayList("TEST", movies);
    }
}
