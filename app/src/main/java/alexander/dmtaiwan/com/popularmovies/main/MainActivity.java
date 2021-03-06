package alexander.dmtaiwan.com.popularmovies.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.detail.DetailActivity;
import alexander.dmtaiwan.com.popularmovies.detail.DetailFragment;
import alexander.dmtaiwan.com.popularmovies.model.Movie;
import alexander.dmtaiwan.com.popularmovies.utilities.Utilities;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainFragment.MovieListener {

    private boolean mTwoPane = false;


    @Nullable
    @BindView(R.id.detail_container)
    FrameLayout mContainer;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        if (mContainer != null) {
            //Container is not null, device is using tablet layout
            mTwoPane = true;
        }
        //Inflate main fragment with MainActivity as listener
        if (savedInstanceState == null) {

            MainFragment mainFragment = MainFragment.newInstance(this, mTwoPane);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, mainFragment, null)
                    .commit();
        }


    }

    @Override
    public void onItemSelected(Movie movie) {
        if (!mTwoPane) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(Utilities.EXTRA_MOVIE, movie);
            startActivity(intent);
        }else{
            replaceFragment(movie);
        }
    }

    @Override
    public void createFragment(Movie movie) {
        replaceFragment(movie);
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

    private void replaceFragment(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(Utilities.EXTRA_MOVIE, movie);
        DetailFragment detailFragment = DetailFragment.newInstance(this, mTwoPane);
        detailFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_container, detailFragment, null)
                .commit();
    }
}
