package alexander.dmtaiwan.com.popularmovies.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.main.MainActivity;
import alexander.dmtaiwan.com.popularmovies.main.MainFragment;
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
    private static final String OUTSTATE_VIDEOS = "outstate_videos";
    private static final String OUTSTATE_REVIEWS = "outstate_reviews";
    private static final String OUTSATE_TWOPANE = "outsate_twopane";

    private Movie mMovie;
    private ArrayList<Video> mVideos = new ArrayList<>();
    private ArrayList<Review> mReviews = new ArrayList<>();

    private DetailAdapter mAdapter;
    private DetailsPresenter mPresenter;
    private MainFragment.MovieListener listener;
    private Boolean mTwoPane = false;

    @BindView(R.id.detail_recycler)
    RecyclerView mRecycler;

    @Nullable
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @Nullable
    @BindView(R.id.image_backdrop)
    ImageView mBackdrop;

    public static DetailFragment newInstance(MainFragment.MovieListener listener, boolean mTwoPane) {
        DetailFragment fragment = new DetailFragment();
        fragment.listener = listener;
        fragment.mTwoPane = mTwoPane;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        mPresenter = new DetailsPresenter(this);

        //If two pane and restartd, reattach listener
        if (savedInstanceState != null && mTwoPane) {
            listener = ((MainActivity) getActivity());
        }

        //getMovies args
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(Utilities.EXTRA_MOVIE);


            //Setup recycler
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            mRecycler.setLayoutManager(llm);
            mAdapter = new DetailAdapter(mMovie, mVideos, mReviews, getActivity());
            mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            mRecycler.setAdapter(mAdapter);

            //Restore state if resuming fragment
            if (savedInstanceState != null) {
                mVideos = savedInstanceState.getParcelableArrayList(OUTSTATE_VIDEOS);
                mReviews = savedInstanceState.getParcelableArrayList(OUTSTATE_REVIEWS);
                mTwoPane = savedInstanceState.getBoolean(OUTSATE_TWOPANE);
                mAdapter.updateReviews(mReviews);
                mAdapter.updateVideos(mVideos);
            }

            //If not two pane, load backdrop image into collapsing toolbar:
            if(!mTwoPane) {
                Picasso.with(getActivity()).load(mMovie.getBackdrop_path()).into(mBackdrop);
            }



            //Fetch data if new fragment
            if (savedInstanceState == null) {
                mPresenter.fetchVideos(mMovie.getId());
                mPresenter.fetchReviews(mMovie.getId());
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
    }

    @Override
    public void onErrorReturned(int error) {
        if (mTwoPane) {
            listener.onError(error);
        }else {
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(OUTSTATE_REVIEWS , mReviews);
        outState.putParcelableArrayList(OUTSTATE_VIDEOS, mVideos);
        outState.putBoolean(OUTSATE_TWOPANE, mTwoPane);
    }
}
