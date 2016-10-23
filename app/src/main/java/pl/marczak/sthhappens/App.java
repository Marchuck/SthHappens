package pl.marczak.sthhappens;

import android.app.Application;

import com.google.android.gms.common.api.Api;
import com.karumi.dexter.Dexter;

/**
 * Project "SthHappens"
 * <p/>
 * Created by Lukasz Marczak
 * on 23.10.16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Dexter.initialize(this);
    }
}
