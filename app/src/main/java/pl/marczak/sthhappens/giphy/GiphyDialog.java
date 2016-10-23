package pl.marczak.sthhappens.giphy;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.felipecsl.gifimageview.library.GifImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.marczak.sthhappens.R;

/**
 * Project "SthHappens"
 * <p/>
 * Created by Lukasz Marczak
 * on 23.10.16.
 */
public class GiphyDialog extends Dialog {

    @BindView(R.id.gifImageView)
    GifImageView gifImageView;

    public static final String TAG = GiphyDialog.class.getSimpleName();

    public GiphyDialog(Context context) {
        super(context);
    }

    public GiphyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected GiphyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    void init(){

        setContentView(R.layout.giphy_dialog);
        ButterKnife.bind(this);


    }

    @Override
    public void show() {
        init();

        super.show();

        new GifDataDownloader() {
            @Override protected void onPostExecute(final byte[] bytes) {
                gifImageView.setBytes(bytes);
                gifImageView.startAnimation();
                Log.d(TAG, "GIF width is " + gifImageView.getGifWidth());
                Log.d(TAG, "GIF height is " + gifImageView.getGifHeight());
            }
        }.execute("https://media.giphy.com/media/yCYIaXD5hrDLG/giphy.gif");
    }
}
