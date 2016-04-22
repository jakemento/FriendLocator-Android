package com.epicodus.friendlocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationDetailsActivity extends AppCompatActivity {
    @Bind(R.id.addressTextView) TextView mAddressTextView;
    @Bind(R.id.nameTextView) TextView mNameTextView;
    @Bind(R.id.detailsTextView) TextView mDetailsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("inputName");
        String address = intent.getStringExtra("inputAddress");
        String details = intent.getStringExtra("inputDetails");
        mAddressTextView.setText(address);
        mNameTextView.setText(name);
        mDetailsTextView.setText(details);

    }
}
