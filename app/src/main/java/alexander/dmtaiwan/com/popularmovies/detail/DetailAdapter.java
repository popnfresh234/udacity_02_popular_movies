package alexander.dmtaiwan.com.popularmovies.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alexander on 6/18/2016.
 */
public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_DETAILS = 0;
    private final int VIEW_TYPE_TRAILERS = 1;
    private final int VIEW_TYPE_REVIEWS = 2;

    private Movie mMovie;
    private List<Video> videos;
    private List<Review> reviews;
    private Context context;


    public DetailAdapter(Movie movie, List<Video> videos, List<Review> reviews, Context context) {
        this.mMovie = movie;
        this.videos = videos;
        this.reviews = reviews;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_DETAILS;
        }

        if (position > 0 && position <= videos.size()) {
            return VIEW_TYPE_TRAILERS;
        } else {
            return VIEW_TYPE_REVIEWS;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DETAILS) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_basic_details, parent, false);
            return new ViewHolderBasic(itemView);
        } else if (viewType == VIEW_TYPE_TRAILERS) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_video, parent, false);
            return new ViewHolderTrailers(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_review, parent, false);
            return new ViewHolderReviews(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_DETAILS:
                ViewHolderBasic viewHolderBasic = (ViewHolderBasic) holder;
                viewHolderBasic.bindViews(context);
                break;
            case VIEW_TYPE_TRAILERS:
                ViewHolderTrailers viewHolderTrailers = (ViewHolderTrailers) holder;
                viewHolderTrailers.bindViews(context, position-1);
                break;
            case VIEW_TYPE_REVIEWS:
                ViewHolderReviews viewHolderReviews = (ViewHolderReviews) holder;
                viewHolderReviews.bindViews(position-1 - videos.size());
        }
    }

    @Override
    public int getItemCount() {
        return 1 + videos.size() + reviews.size();
    }


    public class ViewHolderBasic extends RecyclerView.ViewHolder {


        @BindView(R.id.image_poster)
        ImageView mPoster;

        @BindView(R.id.text_title)
        TextView mTitleText;

        @BindView(R.id.text_release)
        TextView mReleaseDateText;

        @BindView(R.id.text_rating)
        TextView mRatingText;

        public ViewHolderBasic(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(Context context) {

            mTitleText.setText(mMovie.getOriginal_title());
            mReleaseDateText.setText(mMovie.getRelease_date());
            mRatingText.setText(mMovie.getVote_average());
            Picasso.with(context).load(mMovie.getPoster_path()).into(mPoster);
        }
    }

    public class ViewHolderTrailers extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view_trailer)
        ImageView mTrailerImage;

        @BindView(R.id.text_view_trailer)
        TextView mTrailerTitle;


        public ViewHolderTrailers(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(Context context, int position) {
            mTrailerTitle.setText(videos.get(position).getName());
            Picasso.with(context)
                    .load(videos.get(position).getYoutube_thumb())
                    .into(mTrailerImage);
        }

    }

    public class ViewHolderReviews extends RecyclerView.ViewHolder {
        @BindView(R.id.review_author)
        TextView mReviewAuthor;

        @BindView(R.id.review_content)
        TextView mReviewContent;

        public ViewHolderReviews(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(int position) {
            mReviewAuthor.setText(reviews.get(position).getAuthor());
            mReviewContent.setText(reviews.get(position).getContent());
        }
    }

    public void updateVideos(List<Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    public void updateReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

}
