package com.epicodus.friendlocator.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.epicodus.friendlocator.R;

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
//            String itemStr = mySpinner.getSelectedItem().toString();
            Intent specificFriendIntent = new Intent (FindFriendsActivity.this, SpecificFriendActivity.class);
//            specificFriendIntent.putExtra("data", itemStr);
            startActivity(specificFriendIntent);
        }
    }
}
