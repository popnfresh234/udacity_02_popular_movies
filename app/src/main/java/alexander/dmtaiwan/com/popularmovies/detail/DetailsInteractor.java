package alexander.dmtaiwan.com.popularmovies.detail;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import alexander.dmtaiwan.com.popularmovies.model.Review;
import alexander.dmtaiwan.com.popularmovies.model.Video;
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
 * Created by Alexander on 6/18/2016.
 */
public class DetailsInteractor implements IDetailInteractor {

    String baseUrl = "https://api.themoviedb.org/3/movie/";
    String vidoes = "/videos";

    private IDetailPresenter detailPresenter;

    public DetailsInteractor(IDetailPresenter detailPresenter) {
        this.detailPresenter = detailPresenter;
    }

    @Override
    public void fetchVideos(String id) {
        Request request = RequestGenerator.getVideos(id);
        OkHttpClient client = HttpClientFactory.getClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                detailPresenter.onErrorReturned(Utilities.ERROR_NETWORK_FAILED);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ArrayList<Video> videos = null;
                try {
                    videos = JSONParser.parseVidoes(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                    detailPresenter.onErrorReturned(Utilities.ERROR_JSON);
                }
                detailPresenter.onVideosReturned(videos);
            }
        });
    }

    @Override
    public void fetchReviews(String id) {
        Request request = RequestGenerator.getReviews(id);
        OkHttpClient client = HttpClientFactory.getClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                detailPresenter.onErrorReturned(Utilities.ERROR_NETWORK_FAILED);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ArrayList<Review> reviews = null;
                try {
                    reviews = JSONParser.parseReviews(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                    detailPresenter.onErrorReturned(Utilities.ERROR_JSON);
                }
                detailPresenter.onReviewsReturned(reviews);
            }
        });
    }
}
