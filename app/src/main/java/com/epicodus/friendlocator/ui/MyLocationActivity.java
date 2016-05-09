package com.epicodus.friendlocator.ui;

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

import com.epicodus.friendlocator.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyLocationActivity extends AppCompatActivity implements View.OnClickListener  {
    @Bind(R.id.goToMapButton) Button mGoToMapButton;
    @Bind(R.id.address) EditText mAddress;
    @Bind(R.id.favoritesList) ListView mFavoritesList;
    private String[] favoritePlaces = new String[] {"Pioneer Place", "Washington Square Mall", "Powell's Books", "Ground Kontrol", "Coco Donuts", "Bridgeport Mall", "Pittock Mansion", "Epicodus", "Alder St. Food Carts"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);
        ButterKnife.bind(this);

        mGoToMapButton.setOnClickListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, favoritePlaces);
        mFavoritesList.setAdapter(adapter);

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



    }
}
