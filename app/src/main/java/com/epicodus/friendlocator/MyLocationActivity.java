package com.epicodus.friendlocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyLocationActivity extends AppCompatActivity implements View.OnClickListener  {
    @Bind(R.id.pingLocationButton) Button mPingLocationButton;
    @Bind(R.id.name) EditText mName;
    @Bind(R.id.address) EditText mAddress;
    @Bind(R.id.details) EditText mDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        ButterKnife.bind(this);

        mPingLocationButton.setOnClickListener(this);
    }

    @Override
            public void onClick(View v) {
        if (v == mPingLocationButton) {
            String inputName = mName.getText().toString();
            String inputAddress = mAddress.getText().toString();
            String inputDetails = mDetails.getText().toString();
            Intent nameAddressIntent = new Intent(MyLocationActivity.this, LocationDetailsActivity.class);
            nameAddressIntent.putExtra("inputName", inputName);
            nameAddressIntent.putExtra("inputAddress", inputAddress);
            nameAddressIntent.putExtra("inputDetails", inputDetails);
            startActivity(nameAddressIntent);
        }



    }
}
