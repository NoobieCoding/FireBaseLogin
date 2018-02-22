package com.example.tuterdust.testfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText userField, passwordField;
    private Button signInBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        userField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);
        signInBtn = findViewById(R.id.btn1);
        signUpBtn = findViewById(R.id.btn2);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userField.getText().toString();
                String password = passwordField.getText().toString();
                doSignInOperation(email, password);
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view) {
                String email = userField.getText().toString();
                String password = passwordField.getText().toString();
                doSignUpOperation(email, password);
            }
        });

    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), SignIn.class));
        }
    }

    private void doSignUpOperation(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("CREATE", "createUserWithEmail:success");
                            Toast.makeText(MainActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            userProfile();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("CREATE", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    private void userProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(userField.getText().toString()).build();
            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("TEST", "User profile updated");
                    }
                }
            });
        }
    }

    private void doSignInOperation(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("TEST", "Status: " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.d("TEST", "signin with email failed " + task.getException());
                    Toast.makeText(MainActivity.this, "signin failed.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TEST", "signin with email successed " + task.getException());
                    Toast.makeText(MainActivity.this, "signin successed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(mAuth.getCurrentUser());
                }
            }
        });
    }
}
