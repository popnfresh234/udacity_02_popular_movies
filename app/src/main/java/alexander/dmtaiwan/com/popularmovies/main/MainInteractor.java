package alexander.dmtaiwan.com.popularmovies.main;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import alexander.dmtaiwan.com.popularmovies.model.Movie;
import alexander.dmtaiwan.com.popularmovies.network.HttpClientFactory;
import alexander.dmtaiwan.com.popularmovies.network.RequestGenerator;
import alexander.dmtaiwan.com.popularmovies.utilities.JSONParser;
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


    private IMainPresenter mainPresenter;
    public MainInteractor(IMainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    @Override
    public void fetchData(String sortOrder) {
        Request request = RequestGenerator.getMovies(sortOrder);
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
                    ArrayList<Movie> movieList = JSONParser.parse(response.body().string());
                    mainPresenter.onDataReturned(movieList);
                } catch (JSONException e) {
                    e.printStackTrace();
                    mainPresenter.onError(Utilities.ERROR_JSON);
                }

            }
        });
    }
}
