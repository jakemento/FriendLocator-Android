package com.epicodus.friendlocator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.question) ImageView mQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast questionToast = Toast.makeText(MainActivity.this, "This is an app for locating your friends out in public, allowing you to ping your location and trip details.", Toast.LENGTH_LONG);
                questionToast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0,0);
                questionToast.show();
            }
        });

    }
}
