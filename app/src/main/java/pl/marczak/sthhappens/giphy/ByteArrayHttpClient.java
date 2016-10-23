package pl.marczak.sthhappens.giphy;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Project "SthHappens"
 * <p/>
 * Created by Lukasz Marczak
 * on 23.10.16.
 */

public class ByteArrayHttpClient {
    private static final String TAG = "ByteArrayHttpClient";
    private OkHttpClient client = new OkHttpClient();

    public byte[] get(final String urlString) {
        InputStream in = null;
        try {
            final String decodedUrl = URLDecoder.decode(urlString, "UTF-8");
            final URL url = new URL(decodedUrl);
            final Request request = new Request.Builder().url(url).build();
            final Response response = client.newCall(request).execute();
            in = response.body().byteStream();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];//todo: wtf!?

            while ((nRead = in.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            return buffer.toByteArray();
        } catch (final MalformedURLException e) {
            Log.d(TAG, "Malformed URL", e);
        } catch (final OutOfMemoryError e) {
            Log.d(TAG, "Out of memory", e);
        } catch (final UnsupportedEncodingException e) {
            Log.d(TAG, "Unsupported encoding", e);
        } catch (final IOException e) {
            Log.d(TAG, "IO exception", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (final IOException ignored) {
                }
            }
        }
        return null;
    }
}