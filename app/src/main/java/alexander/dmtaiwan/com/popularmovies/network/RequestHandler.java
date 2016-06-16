package alexander.dmtaiwan.com.popularmovies.network;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alexander on 6/16/2016.
 */
public class RequestHandler {
    public static String request(Request request, Context context) throws IOException {
        Log.i("HTTP", request.method() + " : " + request.url());
        OkHttpClient httpClient= HttpClientFactory.getClient();
        Response response = httpClient.newCall(request).execute();
        String body = response.body().string();
        Log.i("HTTP", response.code() + " : " + body);

        if (response.isSuccessful()) {
            return body;
        } else {
            Log.i("HANDLER EXCEPTION", "exception");
            throw new RuntimeException(response.message());
        }
    }
}
