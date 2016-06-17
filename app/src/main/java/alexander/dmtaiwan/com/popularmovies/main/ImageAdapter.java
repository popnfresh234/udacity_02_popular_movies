package alexander.dmtaiwan.com.popularmovies.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.model.Movie;

/**
 * Created by Alexander on 6/17/2016.
 */
public class ImageAdapter extends BaseAdapter {

    private List<Movie> movies;
    private Context context;
    private AdapterListener adapterListener;

    public ImageAdapter(List<Movie> movies, Context context, AdapterListener adapterListener) {
        this.movies = movies;
        this.context = context;
        this.adapterListener = adapterListener;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView imageView;
        if (convertView == null) {
            imageView = (ImageView) inflater.inflate(R.layout.grid_item, null);
        } else imageView = (ImageView) convertView;

        //Load images with picasso
        Picasso.with(context).load(movies.get(position).getPoster_path()).into(imageView);
        //Set listener on ImageView to pass Movie back to MainFragment and then MainActivity to handle click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.onItemClicked(movies.get(position));
            }
        });
        return imageView;
    }

    public void updateData(List<Movie> movies) {
        //Updates list of movies and notifies adapter of new data
        this.movies = movies;
        notifyDataSetChanged();
    }

    //Interface for MainFragment to implement
    public interface AdapterListener {
        void onItemClicked(Movie movie);
    }

}
