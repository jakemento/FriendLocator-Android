package com.epicodus.friendlocator.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyLocationActivity extends AppCompatActivity implements View.OnClickListener {
    private Firebase mSavedLocationRef;


    @Bind(R.id.goToMapButton)
    Button mGoToMapButton;
    @Bind(R.id.address)
    EditText mAddress;
    @Bind(R.id.favoritesList)
    ListView mFavoritesList;
    @Bind(R.id.saveToFavoritesButton)
    Button mSaveToFavorites;
    private ArrayList<String> favoritePlaces = new ArrayList<String>();
    private SharedPreferences mSharedPreferences;
    private Location mLocation;
    private Query mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        ButterKnife.bind(this);
        mSavedLocationRef = new Firebase(Constants.FIREBASE_URL_SAVED_LOCATION);

        ArrayAdapter adapter = new ArrayAdapter(MyLocationActivity.this, android.R.layout.simple_list_item_1, favoritePlaces);
        mFavoritesList.setAdapter(adapter);
//
//        this.retrieveData();
        Firebase.setAndroidContext(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setUpFirebaseQuery();

        mLocation = new Location();

        mGoToMapButton.setOnClickListener(this);
        mSaveToFavorites.setOnClickListener(this);


        mFavoritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String favoritePlace = ((TextView) view).getText().toString();
                Intent intent = new Intent(MyLocationActivity.this, LocationDetailsActivity.class);
                intent.putExtra("favoritePlace", favoritePlace);
                startActivity(intent);
                Toast.makeText(MyLocationActivity.this, favoritePlace, Toast.LENGTH_SHORT).show();

            }
        });


        mSavedLocationRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                String locations = dataSnapshot.getValue().toString();
                favoritePlaces.add(locations);



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

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
            addData(mAddress.getText().toString());
            mAddress.setText("");

            String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);

            Firebase userRestaurantsFirebaseRef = new Firebase(Constants.FIREBASE_URL_SAVED_LOCATION).child(userUid);
            Firebase pushRef = userRestaurantsFirebaseRef.push();
            String locationPushId = pushRef.getKey();
            mLocation.setPushId(locationPushId);

            pushRef.setValue(mLocation);


            Toast notifySaved = Toast.makeText(getApplicationContext(), "Location Saved!", Toast.LENGTH_SHORT);
            notifySaved.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK | Gravity.CENTER_HORIZONTAL, 0, 0);
            notifySaved.show();


        }
    }

    private void setUpFirebaseQuery() {
        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        String location = mSavedLocationRef.child(userUid).toString();
        mQuery = new Firebase(location);

    }


    private void addData(String address) {
        Location location = new Location();
        location.setAddress(address);
        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        mSavedLocationRef.child(userUid).push().setValue(location);
    }
}

//    private void retrieveData() {
//        mSavedLocationRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                getUpdates(dataSnapshot);
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                getUpdates(dataSnapshot);
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
//
//    private void getUpdates(DataSnapshot ds) {
//        favoritePlaces.clear();
//
//        for(DataSnapshot data : ds.getChildren()) {
//            Location location = new Location();
//            location.setAddress(data.getValue(Location.class).getAddress());
//
//            favoritePlaces.add(location.getAddress());
//        }
//
//            ArrayAdapter adapter = new ArrayAdapter(MyLocationActivity.this, android.R.layout.simple_list_item_1, favoritePlaces);
//            mFavoritesList.setAdapter(adapter);
//        }
//    }

