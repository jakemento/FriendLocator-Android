package com.epicodus.friendlocator.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.friendlocator.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.android.gms.maps.CameraUpdateFactory.*;

public class LocationDetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private SupportMapFragment fragment;
    private CameraUpdate point;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_location_details, container, false);
    }

    @Bind(R.id.addressTextView) TextView mAddressTextView;
    @Bind(R.id.nameTextView) TextView mNameTextView;
    @Bind(R.id.detailsTextView) TextView mDetailsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        ButterKnife.bind(this);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();
        String name = intent.getStringExtra("inputName");
        String address = intent.getStringExtra("inputAddress");
        String details = intent.getStringExtra("inputDetails");
        mAddressTextView.setText(address);
        mNameTextView.setText(name);
        mDetailsTextView.setText(details);
        point = CameraUpdateFactory.newLatLng(new LatLng(45.5231, -122.6765));


    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(point);
        map.addMarker(new MarkerOptions()
                .position(new LatLng(45.5231, -122.6765)));

    }
}
