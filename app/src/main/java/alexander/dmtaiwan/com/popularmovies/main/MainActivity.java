package alexander.dmtaiwan.com.popularmovies.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.detail.DetailActivity;
import alexander.dmtaiwan.com.popularmovies.detail.DetailFragment;
import alexander.dmtaiwan.com.popularmovies.model.Movie;
import alexander.dmtaiwan.com.popularmovies.utilities.Utilities;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainFragment.MovieListener {

    private boolean mTwoPane;


    @Nullable
    @BindView(R.id.detail_container)
    FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (mContainer != null) {
            //Container is not null, device is using tablet layout
            mTwoPane = true;
        } else mTwoPane = false;
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

    private void replaceFragment(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(Utilities.EXTRA_MOVIE, movie);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_container, detailFragment, null)
                .commit();
    }
}
