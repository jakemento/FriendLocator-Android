package com.epicodus.friendlocator.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.epicodus.friendlocator.Constants;
import com.epicodus.friendlocator.R;
import com.epicodus.friendlocator.models.Location;
import com.epicodus.friendlocator.models.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindFriendsActivity extends AppCompatActivity implements View.OnClickListener {
    private Firebase mSavedUserRef;
    private ArrayList<String> users = new ArrayList<String>();


    @Bind(R.id.findSpecificFriendButton)
    Button mFindSpecificFriend;
    @Bind(R.id.friendSpinner)
    Spinner mFriendSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
        ButterKnife.bind(this);
        mSavedUserRef = new Firebase(Constants.FIREBASE_URL_USERS);

        mFindSpecificFriend.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FindFriendsActivity.this, android.R.layout.simple_spinner_item, users);
        mFriendSpinner.setAdapter(adapter);

        mSavedUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    System.out.println(user.getName());
                    users.add(user.getName());
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }



    @Override
    public void onClick(View v) {
        if (v == mFindSpecificFriend) {
            Intent specificFriendIntent = new Intent(FindFriendsActivity.this, SpecificFriendActivity.class);
            startActivity(specificFriendIntent);
        }
    }
}
