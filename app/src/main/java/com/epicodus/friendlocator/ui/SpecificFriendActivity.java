package com.epicodus.friendlocator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.epicodus.friendlocator.R;

import butterknife.Bind;

public class SpecificFriendActivity extends AppCompatActivity {
    @Bind(R.id.friendName) TextView mFriendName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Intent friendIntent = getIntent();
//        String friendName = friendIntent.getStringExtra("data");
//        mFriendName.setText(friendName);

    }

}
