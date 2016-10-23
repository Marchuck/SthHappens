package pl.marczak.sthhappens;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import pl.marczak.sthhappens.location.LocationService;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        MultiplePermissionsListener, PoopCallbacks {

    public static final String TAG = MainActivity.class.getSimpleName();

    boolean locationServiceRegistered;
    PoopPresenter poopPresenter;
    SupportMapFragment mapFragment;
    GoogleMap googleMap;
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: new Location");
            Bundle extras = intent.getExtras();
            LatLng myPosition = new LatLng(extras.getDouble("Latitude"), extras.getDouble("Longitude"));
            MapUtils.zoomPlace(googleMap, myPosition, 14);
        }
    };

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fabParent)
    CoordinatorLayout fabParent;

    @BindView(R.id.fabHolder)
    RelativeLayout fabHolder;

    @BindView(R.id.fabShitHere)
    FloatingActionButton fabShitHere;

    @OnClick(R.id.fabLocation)
    void onLocationClicked() {
        if (googleMap != null) {
            LatLng myPosition = MapUtils.myLocation(googleMap);
            if (myPosition != null)
                MapUtils.zoomPlace(googleMap, myPosition, 15);
        }
    }
    @OnLongClick(R.id.fabLocation)
    boolean onLocationClickedLong() {
        Snackbar.make(fabParent, R.string.map_not_ready, Snackbar.LENGTH_SHORT).show();
        return true;
    }


    @OnClick(R.id.fabShitHere)
    void onTryToSettlePoop() {

        Snackbar.make(fabParent, R.string.cannot_place_poop_here, Snackbar.LENGTH_SHORT).show();

        if (googleMap != null) {
            Log.d(TAG, "onTryToSettlePoop: ");
            LatLng myPosition = MapUtils.myLocation(googleMap);
            if (myPosition == null) {
                Snackbar.make(fabHolder, R.string.cannot_place_poop_here, Snackbar.LENGTH_SHORT).show();
                return;
            }
            poopPresenter.onTrySettlePoop(myPosition);
        } else {
            Snackbar.make(fabHolder, R.string.map_not_ready, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        poopPresenter = new PoopPresenter(this);
        setSupportActionBar(toolbar);

        Dexter.checkPermissions(this, MagicConstants.permissions);
    }

    private Fragment getSupportMapFragment() {
        if (mapFragment == null) mapFragment = SupportMapFragment.newInstance();
        return mapFragment;
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        if (locationServiceRegistered) {
            unregisterReceiver(receiver);
            locationServiceRegistered = false;
        }
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        //Broadcast/ Filter
        IntentFilter filter = new IntentFilter();
        filter.addAction(LocationService.BROADCAST_ACTION);
        registerReceiver(receiver, filter);
        locationServiceRegistered = true;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        Log.d(TAG, "onPermissionsChecked: ");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, getSupportMapFragment(), MagicConstants.MAP_FRAGMENT)
                .commitAllowingStateLoss();

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        Toast.makeText(MainActivity.this, R.string.permission_not_granted, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPoopSettled(LatLng position) {

    }

    @Override
    public void onPoopEntered() {
        showEnteredIntoShitDialog();
    }

    void showEnteredIntoShitDialog() {

    }

    @Override
    public void onFailedToSettlePoop(String cause) {

    }

    void showFailedToPlaceShitHere() {

    }
}
