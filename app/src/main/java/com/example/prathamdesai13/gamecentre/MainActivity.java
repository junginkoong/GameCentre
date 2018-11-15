package com.example.prathamdesai13.gamecentre;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Firebase firebaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this); // initialize firebase library in android
        firebaseRef = new Firebase("https://gamecentre-c0c28.firebaseio.com/");
        firebaseRef.child("Users");
        Firebase.goOnline();
        setContentView(R.layout.activity_main);
        addLoginButtonListener();
        addSignUpButtonListener();
    }

    /**
     * Activate the login button.
     */
    private void addLoginButtonListener() {
        Button loginButton = findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLoginActivity();
            }
        });
    }

    /**
     * Activate the sign up button.
     */
    private void addSignUpButtonListener() {
        Button signUp = findViewById(R.id.SignUpButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignUpActivity();
            }
        });
    }

    /**
     * Switch to the LoginActivity view to login.
     */
    private void switchToLoginActivity()
    {
        Intent tmp = new Intent(this, LoginActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the SignUpActivity to sign up
     */
    private void switchToSignUpActivity()
    {
        Intent tmp = new Intent(this, SignUpActivity.class);
        startActivity(tmp);
    }

}

