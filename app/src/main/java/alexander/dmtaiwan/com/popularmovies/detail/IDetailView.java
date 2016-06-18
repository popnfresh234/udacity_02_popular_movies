package alexander.dmtaiwan.com.popularmovies.detail;

import java.util.ArrayList;

import alexander.dmtaiwan.com.popularmovies.model.Review;
import alexander.dmtaiwan.com.popularmovies.model.Video;

/**
 * Created by Alexander on 6/18/2016.
 */
public interface IDetailView {
    void onReviewsReturned(ArrayList<Review> reviews);

    void onVideosReturned(ArrayList<Video> videos);

    void onErrorReturned(int error);
}
