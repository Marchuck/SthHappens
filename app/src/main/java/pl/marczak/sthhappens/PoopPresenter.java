package pl.marczak.sthhappens;

import com.google.android.gms.maps.model.LatLng;

/**
 * Project "SthHappens"
 * <p/>
 * Created by Lukasz Marczak
 * on 23.10.16.
 */
public class PoopPresenter {
    PoopCallbacks poopCallbacks;

    public PoopPresenter(PoopCallbacks poopCallbacks) {
        this.poopCallbacks = poopCallbacks;
    }

    public void onTrySettlePoop(LatLng myPosition) {

    }
}
