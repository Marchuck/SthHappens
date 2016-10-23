package pl.marczak.sthhappens;

import com.google.android.gms.maps.model.LatLng;

/**
 * Project "SthHappens"
 * <p/>
 * Created by Lukasz Marczak
 * on 23.10.16.
 */
public interface PoopCallbacks {

    void onPoopSettled(LatLng position);

    void onPoopEntered();

    void onFailedToSettlePoop(String cause);
}
