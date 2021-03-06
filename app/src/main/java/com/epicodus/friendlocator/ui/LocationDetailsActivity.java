package com.epicodus.friendlocator.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.epicodus.friendlocator.Constants;
import com.epicodus.friendlocator.R;
import com.epicodus.friendlocator.models.Location;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.android.gms.maps.CameraUpdateFactory.*;

public class LocationDetailsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener,
        GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private MapFragment fragment;
    private CameraUpdate point;
    private static final float DEFAULTZOOM = 15;
    private CameraUpdate update;
    private String inputLocation;
    Geocoder geocoder;
    List<Address> addresses;
    private UiSettings mUiSettings;
    @Bind(R.id.saveButton) Button mSaveButton;
    @Bind(R.id.location) EditText mLocation;
    @Bind(R.id.goButton) Button mGoButton;
    @Bind(R.id.toggleTerrain) ImageView mToggleTerrain;
    @Bind(R.id.markerIcon) ImageView mMarkerIcon;
    double newLat;
    double newLong;
    boolean isMapClicked = false;
    private Firebase mSavedLocationRef;
    @Bind(R.id.satelliteView) ImageView mSatelliteView;
    private SharedPreferences mSharedPreferences;
    @Bind(R.id.savedLocationsButton) Button mSavedLocationButton;

    private ArrayList<String> favoritePlaces = new ArrayList<String>();





//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.activity_location_details, container, false);
//    }

    @Bind(R.id.addressTextView) TextView mAddressTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        ButterKnife.bind(this);
        mSaveButton.setOnClickListener(this);
        mMarkerIcon.setOnClickListener(this);
        mSatelliteView.setOnClickListener(this);
        mToggleTerrain.setOnClickListener(this);
        mSavedLocationButton.setOnClickListener(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mSavedLocationRef = new Firebase(Constants.FIREBASE_URL_SAVED_LOCATION);

        Firebase.setAndroidContext(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        String favoritePlace = intent.getStringExtra("favoritePlace");
//        String name = intent.getStringExtra("inputName");
        String address = intent.getStringExtra("inputAddress");
//        String details = intent.getStringExtra("inputDetails");
        mAddressTextView.setText(address);
        point = CameraUpdateFactory.newLatLng(new LatLng(45.5231, -122.6765));
        if (favoritePlace != null) {
            mLocation.setText(favoritePlace);
        }
    }

    @Override
        public void onClick(View v ) {
        if (v == mSaveButton) { String address = mLocation.getText().toString();
            String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
            Firebase userRestaurantsFirebaseRef = new Firebase(Constants.FIREBASE_URL_SAVED_LOCATION).child(userUid);
            Firebase pushRef = userRestaurantsFirebaseRef.push(); Location location = new Location(address);

            pushRef.setValue(location);

            mLocation.setText("");

            Toast notifySaved = Toast.makeText(getApplicationContext(), "Location Saved!", Toast.LENGTH_SHORT);
            notifySaved.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK | Gravity.CENTER_HORIZONTAL, 0, 0);
            notifySaved.show(); }

        if(v ==mSatelliteView) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if(v ==mToggleTerrain) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if(v ==mMarkerIcon) {
            isMapClicked = true;
        }
        if(v == mSavedLocationButton) {
            Intent intent = new Intent(LocationDetailsActivity.this, MyLocationActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mUiSettings = mMap.getUiSettings();
        mMap.setOnMapClickListener(this);
        mMap.moveCamera(point);
        mMap.setOnMapClickListener(this);
        mMap.setBuildingsEnabled(true);

        mUiSettings.setZoomControlsEnabled(true);

        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 1000, null);

        inputLocation = mLocation.getText().toString();
        if (inputLocation.matches("")) {
        }
        else {
            mGoButton.performClick();

        }
    }

    private void goToLocation(double lat, double lng, float zoom) {
        LatLng LatLong = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LatLong, zoom);
        mMap.moveCamera(update);
    }

    public void geoLocate(View v) throws IOException {

        String location = mLocation.getText().toString();
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat = add.getLatitude();
        double lng = add.getLongitude();

        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng)).title(mLocation.getText().toString()));
        marker.showInfoWindow();
        marker.getTitle();

        goToLocation(lat, lng, DEFAULTZOOM);
    }

    @Override
    public void onMapClick(LatLng point) {
        if (isMapClicked == true) {
            double lat = point.latitude;
            double lng = point.longitude;
            newLat = lat;
            newLong = lng;

            isMapClicked = false;

            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(lat, lng, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String address = addresses.get(0).getAddressLine(0);

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng)));
            marker.showInfoWindow();
            marker.setTitle(address);
            dropPinEffect(marker);

        }
    }

    private void dropPinEffect(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post again 15ms later.
                    handler.postDelayed(this, 15);
                } else {
                    marker.showInfoWindow();

                }
            }
        });
    }

}
