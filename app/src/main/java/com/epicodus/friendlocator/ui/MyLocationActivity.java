package com.epicodus.friendlocator.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.friendlocator.Constants;
import com.epicodus.friendlocator.R;
import com.epicodus.friendlocator.models.Location;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyLocationActivity extends AppCompatActivity implements View.OnClickListener  {
    private Firebase mSavedLocationRef;

    @Bind(R.id.goToMapButton) Button mGoToMapButton;
    @Bind(R.id.address) EditText mAddress;
    @Bind(R.id.favoritesList) ListView mFavoritesList;
    @Bind(R.id.saveToFavoritesButton) Button mSaveToFavorites;
    private ArrayList<String> favoritePlaces = new ArrayList<String>();
       String [] places = {"Washington Square Mall", "Powell's Books", "Ground Kontrol", "Coco Donuts", "Bridgeport Mall", "Pittock Mansion", "Epicodus", "Alder St. Food Carts", "Pioneer Place"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        ButterKnife.bind(this);
//        favoritePlaces.addAll(Arrays.asList(places));
        mSavedLocationRef = new Firebase(Constants.FIREBASE_URL_SAVED_LOCATION);

        this.retrieveData();
        Firebase.setAndroidContext(this);

//        mSavedLocationRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String locations = dataSnapshot.getValue().toString();
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });


        mGoToMapButton.setOnClickListener(this);
        mSaveToFavorites.setOnClickListener(this);

//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, favoritePlaces);
//        mFavoritesList.setAdapter(adapter);

        mFavoritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String favoritePlace = ((TextView)view).getText().toString();
                Intent intent = new Intent(MyLocationActivity.this, LocationDetailsActivity.class);
                intent.putExtra("favoritePlace", favoritePlace);
                startActivity(intent);
                Toast.makeText(MyLocationActivity.this, favoritePlace, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
            public void onClick(View v) {
        if (v == mGoToMapButton) {
            String inputAddress = mAddress.getText().toString();
            Intent nameAddressIntent = new Intent(MyLocationActivity.this, LocationDetailsActivity.class);
            nameAddressIntent.putExtra("inputAddress", inputAddress);
            startActivity(nameAddressIntent);
        }

            if (v == mSaveToFavorites) {
//                String location = mAddress.getText().toString();
//                saveLocationToFirebase(location);

                addData(mAddress.getText().toString());


                Toast notifySaved = Toast.makeText(getApplicationContext(), "Location Saved!", Toast.LENGTH_SHORT);
                notifySaved.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK| Gravity.CENTER_HORIZONTAL, 0, 0);
                notifySaved.show();


            }
        }
//        public void saveLocationToFirebase(String location) {
//            Firebase savedLocationRef = new Firebase(Constants.FIREBASE_URL_SAVED_LOCATION);
//            savedLocationRef.push().setValue(location);
//    }

    private void addData(String address) {
        Location location = new Location();
        location.setAddress(address);

        mSavedLocationRef.child("savedLocation").push().setValue(location);
    }

    private void retrieveData() {
        mSavedLocationRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getUpdates(DataSnapshot ds) {
        favoritePlaces.clear();

        for(DataSnapshot data : ds.getChildren()) {
            Location location = new Location();
            location.setAddress(data.getValue(Location.class).getAddress());

            favoritePlaces.add(location.getAddress());
        }

        if(favoritePlaces.size()>0) {
            ArrayAdapter adapter = new ArrayAdapter(MyLocationActivity.this, android.R.layout.simple_list_item_1, favoritePlaces);
            mFavoritesList.setAdapter(adapter);
        }
    }
}
