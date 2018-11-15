package com.example.prathamdesai13.gamecentre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SignUpActivity extends Activity {
    Firebase firebaseRef = new Firebase("https://gamecentre-c0c28.firebaseio.com/");
    Firebase userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        userRef = firebaseRef.child("Users");
        addSignUpButtonListener();
    }

    private void addSignUpButtonListener(){
        Button signupButton = findViewById(R.id.SignUpButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processSignUp();
            }
        });
    }

    private void processSignUp(){

        EditText usernameField = findViewById(R.id.SignUpUsernameInput);
        EditText passwordField = findViewById(R.id.SignUpPasswordInput);

        final String username = usernameField.getText().toString();
        final String password = passwordField.getText().toString();

        userRef.addValueEventListener(new ValueEventListener() {

            boolean newUser = true;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String user = ds.getKey();
                    if (user.equals(username)){
                        newUser = false;
                    }
                }
                if (newUser){
                    userRef.child(username).setValue(password);
                    switchToLoginActivity();
                }else{
                    userNameExists();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    /**
     * Display that the username is taken.
     */
    private void userNameExists() {
        Toast.makeText(this, "Please pick another username, this one is taken", Toast.LENGTH_SHORT).show();
        EditText usernameField = findViewById(R.id.SignUpUsernameInput);
        EditText passwordField = findViewById(R.id.SignUpPasswordInput);
        usernameField.getText().clear();
        passwordField.getText().clear();
    }

    /**
     * Switch to the LoginActivity view to play the game.
     */
    private void switchToLoginActivity()
    {
        Intent tmp = new Intent(this, LoginActivity.class);
        EditText usernameField = findViewById(R.id.SignUpUsernameInput);
        EditText passwordField = findViewById(R.id.SignUpPasswordInput);
        usernameField.getText().clear();
        passwordField.getText().clear();
        startActivity(tmp);
    }

}