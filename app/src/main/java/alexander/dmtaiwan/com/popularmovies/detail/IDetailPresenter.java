package alexander.dmtaiwan.com.popularmovies.detail;

import java.util.List;

import alexander.dmtaiwan.com.popularmovies.model.Review;
import alexander.dmtaiwan.com.popularmovies.model.Video;

/**
 * Created by Alexander on 6/18/2016.
 */
public interface IDetailPresenter {

    void fetchData(String id, int code);

    void onReviewsReturned(List<Review> reviews);

    void onVideosReturned(List<Video> videos);

    void onErrorReturned(int error);
}
