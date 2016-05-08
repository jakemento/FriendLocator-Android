package com.epicodus.friendlocator.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.epicodus.friendlocator.Constants;
import com.epicodus.friendlocator.R;
import com.firebase.client.Firebase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.question) ImageView mQuestionButton;
    @Bind(R.id.findFriendsButton) Button mFindFriendsButton;
    @Bind(R.id.pingLocationButton) Button mPingLocationButton;
    private Firebase mFirebaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mQuestionButton.setOnClickListener(this);
        mFindFriendsButton.setOnClickListener(this);
        mPingLocationButton.setOnClickListener(this);
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);

    }
            @Override
            public void onClick(View v) {
            if (v == mQuestionButton) {
                Toast questionToast = Toast.makeText(MainActivity.this, "This is an app for locating your friends out in public, allowing you to ping your location and trip details.", Toast.LENGTH_LONG);
                questionToast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0,0);
                questionToast.show();
            }
                if( v == mFindFriendsButton) {
                    Intent findFriendsIntent = new Intent(MainActivity.this, FindFriendsActivity.class);
                    startActivity(findFriendsIntent);
                }

                if (v == mPingLocationButton) {
                    Intent myLocationIntent = new Intent (MainActivity.this, MyLocationActivity.class);
                    startActivity(myLocationIntent);
                }
        };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void logout() {
        mFirebaseRef.unauth();
        takeUserToLoginScreenOnUnAuth();
    }

    private void takeUserToLoginScreenOnUnAuth() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
