package alexander.dmtaiwan.com.popularmovies.network;

import android.support.annotation.NonNull;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Alexander on 6/16/2016.
 */
public class RequestGenerator {
    /**
     * Adds default header for all requests
     *
     * @param builder
     */
    private static void addDefaultHeaders(@NonNull Request.Builder builder) {
        builder.header("Accept", "application/json");
    }


    /**
     * Builds a get Request object
     *
     * @param url
     */
    public static Request get(@NonNull String url) {
        RequestBody formBody = new FormBody.Builder()
                .add("api_key", "02134457e3479763fd29902a1e1235c3")
                .build();
        Request.Builder builder = new Request.Builder().url(url).post(formBody);
        return builder.build();
    }
}
