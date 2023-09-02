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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ChangePass extends AppCompatActivity {
    private boolean passVisible = false;
    public EditText username, firstPass, secondPass;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String DOCUMENT_SEARCH = "DOCUMENT_SEARCH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pass);

        username = findViewById(R.id.changepassusername);
        firstPass = findViewById(R.id.firstpass);
        secondPass = findViewById(R.id.secondpass);
    }

    public void loginPage(View view) {
        String user = username.getText().toString();
        String fPass = firstPass.getText().toString();
        String sPass = secondPass.getText().toString();

        if (fPass.length() > 0 && fPass.equals(sPass)) {
            db.collection("users").document(user).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                User user1 = document.toObject(User.class);
                                Log.d(DOCUMENT_SEARCH, "Document found with user " + user);
                                User changedUser = new User(user1.getFirstName(), user1.getLastName(), user1.getUsername(), fPass, user1.getPoints());
                                db.collection("users").document(user).set(changedUser);
                                Toast.makeText(ChangePass.this, "Password changed successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            } else {
                                Log.d(DOCUMENT_SEARCH, "No document found.");
                                Toast.makeText(ChangePass.this, "User " + user + " does not exist", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else if (fPass.length() > 0 && sPass.length() > 0 && !fPass.equals(sPass)) {
            Toast.makeText(ChangePass.this, "Passwords do not match", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ChangePass.this, "Please complete all fields.", Toast.LENGTH_LONG).show();
        }
    }

    public void showPassLogin(View view) {
        if (!passVisible) {
            firstPass.setTransformationMethod(null);
            secondPass.setTransformationMethod(null);
            passVisible = true;
        } else {
            firstPass.setTransformationMethod(new PasswordTransformationMethod());
            secondPass.setTransformationMethod(new PasswordTransformationMethod());
            passVisible = false;
        }
    }
}
