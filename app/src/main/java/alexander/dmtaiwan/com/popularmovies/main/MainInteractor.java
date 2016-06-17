package alexander.dmtaiwan.com.popularmovies.main;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import alexander.dmtaiwan.com.popularmovies.model.Movie;
import alexander.dmtaiwan.com.popularmovies.network.HttpClientFactory;
import alexander.dmtaiwan.com.popularmovies.network.RequestGenerator;
import alexander.dmtaiwan.com.popularmovies.utilities.MovieParser;
import alexander.dmtaiwan.com.popularmovies.utilities.Utilities;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alexander on 6/17/2016.
 */
public class MainInteractor implements IMainInteractor {

    private String baseUrl = "http://api.themoviedb.org/3/movie/popular";
    private IMainPresenter mainPresenter;

    public MainInteractor(IMainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void fetchData(String sortOrder) {
        List<Movie> movieList = new ArrayList<>();

        Request request = RequestGenerator.get(baseUrl);
        OkHttpClient client = HttpClientFactory.getClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                mainPresenter.onError(Utilities.ERROR_NETWORK_FAILED);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("RESPONSE", "CODE " + response.code());
                try {
                    List<Movie> movieList = MovieParser.parse(response.body().string());
                    mainPresenter.onDataReturned(movieList);
                } catch (JSONException e) {
                    e.printStackTrace();
                    mainPresenter.onError(Utilities.ERROR_JSON);
                }

            }
        });
    }
}
