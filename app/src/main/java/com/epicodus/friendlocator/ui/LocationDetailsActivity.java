package com.epicodus.friendlocator.ui;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.friendlocator.R;
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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.android.gms.maps.CameraUpdateFactory.*;

public class LocationDetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapFragment fragment;
    private CameraUpdate point;
    private static final float DEFAULTZOOM = 15;
    private CameraUpdate update;
    private String inputLocation;
    private UiSettings mUiSettings;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_location_details, container, false);
    }

    @Bind(R.id.addressTextView) TextView mAddressTextView;
    @Bind(R.id.nameTextView) TextView mNameTextView;
    @Bind(R.id.detailsTextView) TextView mDetailsTextView;
    @Bind(R.id.location) EditText mLocation;
    @Bind(R.id.goButton) Button mGoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        String favoritePlace = intent.getStringExtra("favoritePlace");
        String name = intent.getStringExtra("inputName");
        String address = intent.getStringExtra("inputAddress");
        String details = intent.getStringExtra("inputDetails");
        mAddressTextView.setText(address);
        mNameTextView.setText(name);
        mDetailsTextView.setText(details);
        point = CameraUpdateFactory.newLatLng(new LatLng(45.5231, -122.6765));
        if (favoritePlace != null) {
            mLocation.setText(favoritePlace);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mUiSettings = mMap.getUiSettings();
        mMap.moveCamera(point);
        mMap.setBuildingsEnabled(true);
        mUiSettings.setZoomControlsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 1000, null);

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

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng)));


        goToLocation(lat, lng, DEFAULTZOOM);
    }

}
