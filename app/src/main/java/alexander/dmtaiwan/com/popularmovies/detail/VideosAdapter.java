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
import alexander.dmtaiwan.com.popularmovies.model.Video;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 6/18/2016.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {
    private List<Video> mVideoList;
    private Context context;

    public VideosAdapter(List<Video> videoList, Context context) {
        this.context = context;
        this.mVideoList = videoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_video, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(mVideoList.get(position).getYoutube_thumb()).into(holder.mTrailerImage);
        holder.mTrailerTitleText.setText(mVideoList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (mVideoList != null) {
            return mVideoList.size();
        }else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view_trailer)
        ImageView mTrailerImage;

        @BindView(R.id.text_view_trailer)
        TextView mTrailerTitleText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateData(List<Video> videoList) {

        this.mVideoList = videoList;
        notifyDataSetChanged();
    }

}
