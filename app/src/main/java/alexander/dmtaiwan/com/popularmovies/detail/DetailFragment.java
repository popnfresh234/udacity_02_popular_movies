package alexander.dmtaiwan.com.popularmovies.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import alexander.dmtaiwan.com.popularmovies.R;
import alexander.dmtaiwan.com.popularmovies.model.Movie;
import alexander.dmtaiwan.com.popularmovies.utilities.Utilities;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 6/16/2016.
 */
public class DetailFragment extends Fragment {
    private Movie mMovie;

    @BindView(R.id.test_text)
    TextView testText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);

        //get args
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(Utilities.EXTRA_MOVIE);
            testText.setText(mMovie.getOriginal_title());
        }
        return rootView;
    }
}
