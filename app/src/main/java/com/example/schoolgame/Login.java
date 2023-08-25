package com.example.schoolgame;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    private boolean passVisible = false;
    public EditText username, password;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String DOCUMENT_SEARCH = "DOCUMENT_SEARCH";
    public static final String USER = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = findViewById(R.id.usernamelogin);
        password = findViewById(R.id.passwordlogin);
    }

    public void loginPage(View view) {
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (user.length() > 0 && pass.length() > 0) {
            db.collection("users").document(user).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User user1 = documentSnapshot.toObject(User.class);
                            if (User.Encryption.decrypt(user1.getEncryptedPass()).equals(pass)) {
                                Log.d(DOCUMENT_SEARCH, "Document found with user " + user);
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                                intent.putExtra(USER, user);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Log.w(DOCUMENT_SEARCH, "get failed with ", e);
                            Toast.makeText(Login.this, "No user found for " + user, Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(Login.this, "Please complete all fields.", Toast.LENGTH_LONG).show();
        }
    }

    public void showPassLogin(View view) {
        if (!passVisible) {
            password.setTransformationMethod(null);
            passVisible = true;
        } else {
            password.setTransformationMethod(new PasswordTransformationMethod());
            passVisible = false;
        }
    }
}
