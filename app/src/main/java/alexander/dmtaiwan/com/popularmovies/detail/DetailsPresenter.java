package alexander.dmtaiwan.com.popularmovies.detail;

import java.util.List;

import alexander.dmtaiwan.com.popularmovies.model.Review;
import alexander.dmtaiwan.com.popularmovies.model.Video;

/**
 * Created by Alexander on 6/18/2016.
 */
public class DetailsPresenter implements IDetailPresenter {

    private IDetailView detailView;
    private IDetailInteractor detailInteractor;

    public DetailsPresenter(IDetailView detailView) {
        this.detailView = detailView;
        this.detailInteractor = new DetailsInteractor(this);
    }


    @Override
    public void fetchVideos(String id) {
        detailInteractor.fetchVideos(id);
    }

    @Override
    public void fetchReviews(String id) {
        detailInteractor.fetchReviews(id);
    }


    @Override
    public void onReviewsReturned(List<Review> reviews) {
        detailView.onReviewsReturned(reviews);
    }

    @Override
    public void onVideosReturned(List<Video> videos) {
        detailView.onVideosReturned(videos);
    }

    @Override
    public void onErrorReturned(int error) {
        detailView.onErrorReturned(error);
    }
}
