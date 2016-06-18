package alexander.dmtaiwan.com.popularmovies.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.model.Movie;
import alexander.dmtaiwan.com.popularmovies.model.Review;
import alexander.dmtaiwan.com.popularmovies.model.Video;
import alexander.dmtaiwan.com.popularmovies.utilities.DividerItemDecoration;
import alexander.dmtaiwan.com.popularmovies.utilities.Utilities;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 6/16/2016.
 */
public class DetailFragment extends Fragment implements IDetailView {
    private Movie mMovie;
    private ArrayList<Video> mVideos = new ArrayList<>();
    private ArrayList<Review> mReviews = new ArrayList<>();

    private DetailAdapter mAdapter;
    private DetailsPresenter mPresenter;

    @BindView(R.id.detail_recycler)
    RecyclerView mRecycler;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.image_backdrop)
    ImageView mBackdrop;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        mPresenter = new DetailsPresenter(this);

        //getMovies args
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(Utilities.EXTRA_MOVIE);
            Picasso.with(getActivity()).load(mMovie.getBackdrop_path()).into(mBackdrop);
            //Setup recycler
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            mRecycler.setLayoutManager(llm);
            mAdapter = new DetailAdapter(mMovie, mVideos, mReviews, getActivity());
            mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            mRecycler.setAdapter(mAdapter);

            //Fetch data
            if (savedInstanceState == null) {
                mPresenter.fetchVideos(mMovie.getId());
                mPresenter.fetchReviews(mMovie.getId());
            }

            if (savedInstanceState != null) {
                mVideos = savedInstanceState.getParcelableArrayList("videos");
                mReviews = savedInstanceState.getParcelableArrayList("reviews");
                mAdapter.updateReviews(mReviews);
                mAdapter.updateVideos(mVideos);
            }


        }

        return rootView;
    }

    @Override
    public void onReviewsReturned(final ArrayList<Review> reviews) {
        //Set data
        //Need to run on UI thread since OkHTTP is running in background thread
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mReviews = reviews;
                    mAdapter.updateReviews(reviews);
                }
            });
        }
        Log.i("WOO", String.valueOf(reviews.size()));
    }

    @Override
    public void onVideosReturned(final ArrayList<Video> videos) {

        //Set data
        //Need to run on UI thread since OkHTTP is running in background thread
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mVideos = videos;
                    mAdapter.updateVideos(videos);
                }
            });
        }
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("reviews" , mReviews);
        outState.putParcelableArrayList("videos", mVideos);
    }
}
