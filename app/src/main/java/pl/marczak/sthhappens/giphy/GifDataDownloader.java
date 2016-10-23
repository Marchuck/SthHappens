package pl.marczak.sthhappens.giphy;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Project "SthHappens"
 * <p/>
 * Created by Lukasz Marczak
 * on 23.10.16.
 */
public class GifDataDownloader extends AsyncTask<String, Void, byte[]> {
    private static final String TAG = "GifDataDownloader";
    private static ByteArrayHttpClient client = new ByteArrayHttpClient();

    @Override
    protected byte[] doInBackground(final String... params) {
        final String gifUrl = params[0];

        if (gifUrl == null)
            return null;

        try {
            return client.get(gifUrl);
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "GifDecode OOM: " + gifUrl, e);
            return null;
        }
    }
}
