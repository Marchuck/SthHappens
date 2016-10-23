package pl.marczak.sthhappens;

import android.location.Location;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import rx.AsyncEmitter;
import rx.Observable;
import rx.functions.Action1;

/**
 * @author Lukasz Marczak
 * @since 24.09.16.
 */
public class MapUtils {

    public static final String TAG = MapUtils.class.getSimpleName();

    public static Observable<GoogleMap> getMap(final SupportMapFragment mapFragment) {

        return Observable.fromAsync(new Action1<AsyncEmitter<GoogleMap>>() {

            @Override
            public void call(final AsyncEmitter<GoogleMap> asyncEmitter) {
                Log.d(TAG, "map fragment done");

                OnMapReadyCallback mapCallback = new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        Log.i(TAG, "onMapReady: ");
                        asyncEmitter.onNext(googleMap);
                        asyncEmitter.onCompleted();
                    }
                };

                mapFragment.getMapAsync(mapCallback);
            }
        }, AsyncEmitter.BackpressureMode.LATEST);
    }

    public static void zoomPlace(GoogleMap googleMap, LatLng position, float zoomLevel) {
        CameraPosition cameraPosition = new CameraPosition(position, zoomLevel, 60, 12);
        final CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.animateCamera(cameraUpdate);
    }

    @Nullable
    public static LatLng myLocation(GoogleMap googleMap) {

        Location location = googleMap.getMyLocation();
        if (location == null) return null;
        return new LatLng(
                location.getLatitude(), location.getLongitude()
        );
    }
}
