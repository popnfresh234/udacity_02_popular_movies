package alexander.dmtaiwan.com.popularmovies.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.SettingsActivity;
import alexander.dmtaiwan.com.popularmovies.model.Movie;
import alexander.dmtaiwan.com.popularmovies.utilities.Utilities;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 6/16/2016.
 */
public class MainFragment extends Fragment implements IMainView , ImageAdapter.AdapterListener, SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String OUTSTATE_MOVIES = "outstate_movies";



    @BindView(R.id.gridview)
    GridView mGridView;



    private ArrayList<Movie> mMovies = new ArrayList<Movie>();
    private ImageAdapter mAdapter;
    private MovieListener listener;
    private boolean mTwoPane;
    private MainPresenter mPresenter;

    public static MainFragment newInstance(MovieListener listener, boolean twoPane) {
        MainFragment fragment = new MainFragment();
        fragment.listener = listener;
        fragment.mTwoPane = twoPane;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.registerOnSharedPreferenceChangeListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);


        if (savedInstanceState != null) {
            mMovies = savedInstanceState.getParcelableArrayList(OUTSTATE_MOVIES);
            //Reattach listener
            listener = (MainActivity)getActivity();
        }

        //Setup the adapter
        mAdapter = new ImageAdapter(mMovies, getActivity(), this);
        mGridView.setAdapter(mAdapter);

        //Get preferred sort option
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.pref_key_sort), getString(R.string.pref_sort_popular));
        Log.i("TEST", sortOrder);

        //Create presenter and fetch data if network is available
        //If no network, display error
        mPresenter = new MainPresenter(this);
        if (Utilities.isNetworkAvailable(getActivity())) {
            if (savedInstanceState == null) {
                mPresenter.fetchData(sortOrder);
            }

        }else onError(Utilities.ERROR_NETWORK_UNAVAILABLE);


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDataReturned(final ArrayList<Movie> movies) {
        //Set data
        //Need to run on UI thread since OkHTTP is running in background thread
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //if two pane notify MainActivity to attach a DetailFragment
                    if (mTwoPane) {
                        listener.createFragment(movies.get(0));
                    }
                    mMovies = movies;
                    mAdapter.updateData(movies);
                }
            });
        }
    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onItemClicked(Movie movie) {
        //Handle clicks from the Adapter
        listener.onItemSelected(movie);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        //Listen for chagnes in sort option
        if (key.equals(getString(R.string.pref_key_sort))) {
            String sortOrder = sharedPreferences.getString(getString(R.string.pref_key_sort), getString(R.string.pref_sort_popular));
            //If the sort order is not favorites, query API for new data
            if(!sortOrder.equals(getString(R.string.pref_sort_favorites))) {
                mPresenter.fetchData(sortOrder);
            }else{
                //If the sort order is favorites, get data from shared prefs
            }
        }
    }


    //Interface for MainActivity to implement
    public interface MovieListener {
        //Handles click
        void onItemSelected(Movie movie);
        //Creates fragment
        void createFragment(Movie movie);
        //Handle errors
        void onError(int error);
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(OUTSTATE_MOVIES, mMovies);
    }
}
