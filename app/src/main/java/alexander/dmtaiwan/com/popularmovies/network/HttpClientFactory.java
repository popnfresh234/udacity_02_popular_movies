package alexander.dmtaiwan.com.popularmovies.network;

import net.jcip.annotations.GuardedBy;

import okhttp3.OkHttpClient;

/**
 * Created by Alexander on 6/16/2016.
 */
public class HttpClientFactory {
    private static final Object LOCK = new Object();
    private static final int TIMEOUT_IN_MS = 10000;

    @GuardedBy("LOCK")
    private static OkHttpClient mOkHttpClient;

    public static OkHttpClient getClient() {
        synchronized (LOCK) {
            if (mOkHttpClient == null) {
                mOkHttpClient = new OkHttpClient();
            }
            return mOkHttpClient;
        }
    }
}
