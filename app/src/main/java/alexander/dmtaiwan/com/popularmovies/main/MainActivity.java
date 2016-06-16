package alexander.dmtaiwan.com.popularmovies.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.List;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.detail.DetailFragment;
import alexander.dmtaiwan.com.popularmovies.model.Movie;
import alexander.dmtaiwan.com.popularmovies.network.ApiService;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private String baseUrl = "http://api.themoviedb.org";

    @Nullable
    @BindView(R.id.detail_container)
    FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<List<Movie>> movies = service.listMovies("02134457e3479763fd29902a1e1235c3");
        movies.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> testList = response.body();
                Log.i("SIZE", String.valueOf(testList.size()));
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                t.printStackTrace();

            }
        });
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
}
