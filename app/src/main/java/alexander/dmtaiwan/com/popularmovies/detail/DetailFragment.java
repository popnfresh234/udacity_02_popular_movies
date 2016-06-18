package alexander.dmtaiwan.com.popularmovies.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.model.Movie;
import alexander.dmtaiwan.com.popularmovies.model.Review;
import alexander.dmtaiwan.com.popularmovies.model.Video;
import alexander.dmtaiwan.com.popularmovies.utilities.Utilities;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 6/16/2016.
 */
public class DetailFragment extends Fragment implements IDetailView {
    private Movie mMovie;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.image_backdrop)
    ImageView mBackdrop;

    @BindView(R.id.image_poster)
    ImageView mPoster;

    @BindView(R.id.text_title)
    TextView mTitleText;

    @BindView(R.id.text_release)
    TextView mReleaseDateText;

    @BindView(R.id.text_rating)
    TextView mRatingText;

    @BindView(R.id.text_overview)
    TextView mOverviewText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        DetailsPresenter presenter = new DetailsPresenter(this);
        //getMovies args
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(Utilities.EXTRA_MOVIE);

            if (savedInstanceState == null) {
                presenter.fetchVideos(mMovie.getId());
                presenter.fetchReviews(mMovie.getId());
            }

            mTitleText.setText(mMovie.getOriginal_title());
            mReleaseDateText.setText(mMovie.getRelease_date());
            mRatingText.setText(mMovie.getVote_average());
            mOverviewText.setText(mMovie.getOverview());

            Picasso.with(getActivity()).load(mMovie.getBackdrop_path()).into(mBackdrop);
            Picasso.with(getActivity()).load(mMovie.getPoster_path()).into(mPoster);


        }

        return rootView;
    }

    @Override
    public void onReviewsReturned(List<Review> reviews) {
        Log.i("WOO", String.valueOf(reviews.size()));
    }

    @Override
    public void onVideosReturned(List<Video> videos) {
        Log.i("YEP", String.valueOf(videos.size()));
    }

    @Override
    public void onErrorReturned(int error) {
        Snackbar snackbar;
        switch (error) {
            case Utilities.ERROR_NETWORK_UNAVAILABLE:
                snackbar = Snackbar.make(mCoordinatorLayout, getString(R.string.error_network_unavailable), Snackbar.LENGTH_LONG);
                break;
            case Utilities.ERROR_NETWORK_FAILED:
                snackbar = Snackbar.make(mCoordinatorLayout, getString(R.string.error_network_failed), Snackbar.LENGTH_LONG);
                break;
            case Utilities.ERROR_JSON:
                snackbar = Snackbar.make(mCoordinatorLayout, getString(R.string.error_json), Snackbar.LENGTH_LONG);
                break;
            default:
                snackbar = Snackbar.make(mCoordinatorLayout, "WEEEEEEEEEEEEE", Snackbar.LENGTH_LONG);
                break;
        }
        snackbar.show();
    }
}
