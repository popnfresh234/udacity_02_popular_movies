package alexander.dmtaiwan.com.popularmovies.detail;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

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
    public void fetchData(String id, final int code) {
        Request request;
        //Generate request and client
        switch (code) {
            case Utilities.REVIEW_CODE:
                request = RequestGenerator.getReviews(id);
                break;
            case Utilities.VIDEO_CODE:
                request = RequestGenerator.getVideos(id);
                break;
            default:
                request = RequestGenerator.getVideos(id);
        }

        OkHttpClient client = HttpClientFactory.getClient();

        //make call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                detailPresenter.onErrorReturned(Utilities.ERROR_NETWORK_FAILED);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Log.i("CODE", String.valueOf(code));
                    //If fetching videos:
                    if (code == Utilities.VIDEO_CODE) {
                        List<Video> videos = JSONParser.parseVidoes(response.body().string());
                        detailPresenter.onVideosReturned(videos);
                    }

                    //If fetching reviews:
                    else if (code == Utilities.REVIEW_CODE) {
                        List<Review> reviews = JSONParser.parseReviews(response.body().string());
                        detailPresenter.onReviewsReturned(reviews);
                    }

                    //Otherwise error
                    else detailPresenter.onErrorReturned(Utilities.ERROR_NETWORK_FAILED);


                } catch (JSONException e) {
                    e.printStackTrace();
                    detailPresenter.onErrorReturned(Utilities.ERROR_JSON);
                }
            }
        });
    }
}
