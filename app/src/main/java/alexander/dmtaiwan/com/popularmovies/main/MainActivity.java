package alexander.dmtaiwan.com.popularmovies.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.detail.DetailFragment;
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

        //Inflate main fragment with MainActivity as listener
        MainFragment mainFragment = MainFragment.newInstance(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, mainFragment, null)
                .commit();

        if (mContainer != null) {
            //Container is not null, device is using tablet layout
            mTwoPane = true;
            if (savedInstanceState == null) {
                //If this is the first time running the app, create and inflate a detail fragment
                DetailFragment detailFragment = new DetailFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.detail_container, detailFragment, null)
                        .commit();
            }
        }else{
            Log.i("SINGLE", "single");
        }

    }

    @Override
    public void onItemSelected(String id) {
        Log.i("CLICK", id);
    }
}
