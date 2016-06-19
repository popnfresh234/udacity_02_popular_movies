package alexander.dmtaiwan.com.popularmovies.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.model.Movie;
import alexander.dmtaiwan.com.popularmovies.utilities.Utilities;

/**
 * Created by lenovo on 6/16/2016.
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //If intent has Movie attached, getMovies it and create fragment
        if (getIntent().getParcelableExtra(Utilities.EXTRA_MOVIE) != null && savedInstanceState == null) {
            Movie movie = getIntent().getParcelableExtra(Utilities.EXTRA_MOVIE);
            DetailFragment fragment = new DetailFragment();

            //Attach movie as arguments
            Bundle args = new Bundle();
            args.putParcelable(Utilities.EXTRA_MOVIE, movie);
            fragment.setArguments(args);

            //Fragment transaction
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();
        }
    }
}
