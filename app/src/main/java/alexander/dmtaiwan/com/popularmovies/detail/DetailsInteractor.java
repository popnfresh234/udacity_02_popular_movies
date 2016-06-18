package alexander.dmtaiwan.com.popularmovies.detail;

/**
 * Created by Alexander on 6/18/2016.
 */
public class DetailsInteractor implements IDetailInteractor {

    private IDetailPresenter detailPresenter;

    public DetailsInteractor(IDetailPresenter detailPresenter) {
        this.detailPresenter = detailPresenter;
    }

    @Override
    public void fetchReviews(String id) {

    }

    @Override
    public void fetchVideos(String id) {

    }
}
