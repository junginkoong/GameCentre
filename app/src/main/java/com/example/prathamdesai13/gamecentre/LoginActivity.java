package com.example.prathamdesai13.gamecentre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class LoginActivity extends Activity {

    Firebase firebaseRef = new Firebase("https://gamecentre-c0c28.firebaseio.com/");
    Firebase userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        userRef = firebaseRef.child("Users");
        addLoginButtonListener();

    }

    private void addLoginButtonListener(){

        Button loginButton = findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processLogin();
            }
        });
    }

    private void processLogin() {
        EditText usernameText = findViewById(R.id.LoginUsernameInput);
        EditText passwordText = findViewById(R.id.LoginPasswordInput);

        final String username = usernameText.getText().toString();
        final String password = passwordText.getText().toString();

        userRef.addValueEventListener(new ValueEventListener() {

            boolean registeredUser = false;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String user = ds.getKey();
                    String pass = ds.getValue().toString();

                    if (user.equals(username) && pass.equals(password)){
                        registeredUser = true;
                        break;
                    }
                }
                if (registeredUser){
                    switchToLandingPageActivity(username);
                }
                else{
                    incorrectCredentials();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    /**
     * Display that password was inputted unsuccessfully.
     */
    private void incorrectCredentials() {
        Toast.makeText(this, "Your password or username was entered incorrectly or maybe you dont exist.", Toast.LENGTH_SHORT).show();
        EditText usernameText = findViewById(R.id.LoginUsernameInput);
        EditText passwordText = findViewById(R.id.LoginPasswordInput);
        usernameText.getText().clear();
        passwordText.getText().clear();
    }

    /**
     * Switch to the LandingPageActivity view to play the game.
     */
    private void switchToLandingPageActivity(String username)
    {
        Intent tmp = new Intent(this, GameMenu.class);
        tmp.putExtra("USERNAME", username);
        startActivity(tmp);
    }

}