package com.example.schoolgame;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SignUp extends AppCompatActivity {
    private boolean passVisible = false;
    public EditText firstName, lastName, username, password;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String DOCUMENT_SEARCH = "DOCUMENT_SEARCH";
    private static final String DOCUMENT_CREATE = "DOCUMENT_CREATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        username = findViewById(R.id.usernamesignup);
        password = findViewById(R.id.passwordsignup);
    }

    public void signupPage(View view) {
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (first.length() > 0 && last.length() > 0 && user.length() > 0 && pass.length() > 0) {
            db.collection("users").document(user).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(DOCUMENT_SEARCH, "This document already exists.");
                                Toast.makeText(SignUp.this, "User " + user + " already exists", Toast.LENGTH_LONG).show();
                            } else {
                                Log.d(DOCUMENT_CREATE, "Creating new document for user " + user);
                                User newUser = new User(first, last, user, pass, 0);
                                db.collection("users").document(user).set(newUser);
                                Toast.makeText(SignUp.this, "New user created successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }
                        } else {
                            Log.d(DOCUMENT_SEARCH, "get failed with ", task.getException());
                        }
                    }
                });
        } else {
            Toast.makeText(SignUp.this, "Please complete all fields.", Toast.LENGTH_LONG).show();
        }
    }

    public void showPassSignup(View view) {
        if (!passVisible) {
            password.setTransformationMethod(null);
            passVisible = true;
        } else {
            password.setTransformationMethod(new PasswordTransformationMethod());
            passVisible = false;
        }
    }
}
