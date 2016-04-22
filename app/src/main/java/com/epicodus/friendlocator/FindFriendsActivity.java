package com.epicodus.friendlocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FindFriendsActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.findSpecificFriendButton) Button mFindSpecificFriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
        ButterKnife.bind(this);

        mFindSpecificFriend.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mFindSpecificFriend) {
            Intent specificFriendIntent = new Intent (FindFriendsActivity.this, SpecificFriendActivity.class);
            startActivity(specificFriendIntent);
        }
    }
}
