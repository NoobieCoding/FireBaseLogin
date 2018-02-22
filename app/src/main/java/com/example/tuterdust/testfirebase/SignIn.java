package com.example.tuterdust.testfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView emailLabel;
    private Button signOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        emailLabel = findViewById(R.id.textView);
        signOutBtn = findViewById(R.id.signout_btn);
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            emailLabel.setText(user.getDisplayName());
        }

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
