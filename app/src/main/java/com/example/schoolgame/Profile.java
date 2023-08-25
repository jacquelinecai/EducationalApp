package com.example.schoolgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    TextView showPoints, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        showPoints = findViewById(R.id.pointsProfile);
        username = findViewById(R.id.user);
        Intent fromMain = getIntent();
        showPoints.setText(fromMain.getStringExtra(MainScreen.PROF_POINTS) + " points");
        username.setText(fromMain.getStringExtra(MainScreen.USER));
    }
}
