package alexander.dmtaiwan.com.popularmovies.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import java.io.IOException;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.detail.DetailFragment;
import alexander.dmtaiwan.com.popularmovies.network.HttpClientFactory;
import alexander.dmtaiwan.com.popularmovies.network.RequestGenerator;
import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private String baseUrl = "http://api.themoviedb.org/3/movie/popular";

    @Nullable
    @BindView(R.id.detail_container)
    FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Request request = RequestGenerator.get(baseUrl);
        OkHttpClient client = HttpClientFactory.getClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("RESPONSE", "CODE " + response.code());
                String body = response.body().string();
                Log.i("RESPONSE", body);
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
