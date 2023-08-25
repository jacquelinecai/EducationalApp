package com.example.schoolgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainScreen extends AppCompatActivity {

    TextView points;
    int pts = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    User profUser;
    String username;

    public static final String PROF_POINTS = "0";
    public static final String USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        Intent intent = getIntent();
        String user = intent.getStringExtra(Login.USER);
        username = user;

        db.collection("users")
                .document(user)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        profUser = documentSnapshot.toObject(User.class);
                        points = findViewById(R.id.points);
                        pts = profUser.getPoints();
                        points.setText(pts + " points");
                    }
                });
    }

    public void profile(View view) {
        Intent intent = new Intent(this, Profile.class);
        intent.putExtra(PROF_POINTS, String.valueOf(pts));
        Intent fromLogin = getIntent();
        String user = fromLogin.getStringExtra(Login.USER);
        intent.putExtra(USER, user);
        startActivity(intent);
    }

    public void pset(View view) {
        Intent intent = new Intent(this, Pset.class);
        startActivityForResult(intent, 1000);
    }

    public void video(View view) {
        Intent intent = new Intent(this, Video.class);
        startActivityForResult(intent, 3000);
    }

    public void essay(View view) {
        Intent intent = new Intent(this, Essay.class);
        startActivityForResult(intent, 2000);
    }

    public void lab(View view) {
        Intent intent = new Intent(this, Lab.class);
        startActivityForResult(intent, 4000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        int point = 0;
        if (requestCode == 1000) {
            point = data.getIntExtra(Pset.POINTS, 0);
        }
        if (requestCode == 2000) {
            point = data.getIntExtra(Essay.POINTS, 0);
        }
        if (requestCode == 3000) {
            point = data.getIntExtra(Video.POINTS, 0);
        }
        if (requestCode == 4000) {
            point = data.getIntExtra(Lab.POINTS, 0);
        }
        pts += point;
        db.collection("users")
                .document(username)
                .update("points", pts);
        points.setText(pts + " points");
    }
}