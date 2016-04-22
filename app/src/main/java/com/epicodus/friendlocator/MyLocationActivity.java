package com.epicodus.friendlocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyLocationActivity extends AppCompatActivity implements View.OnClickListener  {
    @Bind(R.id.pingLocationButton) Button mPingLocationButton;
    @Bind(R.id.name) EditText mName;
    @Bind(R.id.address) EditText mAddress;
    @Bind(R.id.details) EditText mDetails;
    @Bind(R.id.favoritesList) ListView mFavoritesList;
    private String[] favoritePlaces = new String[] {"Pioneer Place", "Washington Square Mall", "Powell's Books", "Ground Kontrol", "Coco Donuts", "Home", "Parent's Place", "Bridgeport Mall", "Pittock Mansion", "Epicodus Building", "Alder St. Food Carts"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        ButterKnife.bind(this);

        mPingLocationButton.setOnClickListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, favoritePlaces);
        mFavoritesList.setAdapter(adapter);

        mFavoritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String favoritePlace = ((TextView)view).getText().toString();
                Toast.makeText(MyLocationActivity.this, favoritePlace, Toast.LENGTH_SHORT).show();
            }
        });
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
