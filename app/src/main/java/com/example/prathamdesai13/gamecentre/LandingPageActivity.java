package com.example.prathamdesai13.gamecentre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;


public class LandingPageActivity extends Activity {


    /**
     * The user logged in
     */
    private User user;

    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        addStartGameButtonListener();
        addStatsButtonListener();
        user = (User) getIntent().getSerializableExtra("user");
    }

    /**
     * Activate the start game button.
     */
    private void addStartGameButtonListener() {
        Button tilegame = findViewById(R.id.StartTileGameButton);
        tilegame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToStartingActivity();
            }
        });
    }

    /**
     * Activate the stats button.
     */
    private void addStatsButtonListener() {
        Button stat = findViewById(R.id.StatButton);
        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToStats();

            }
        });
    }



    /**
     * Switch to the StartingActivity view to play the game.
     */
    private void switchToStartingActivity()
    {
        Intent tmp = new Intent(this, SlidingTiles.class);
        tmp.putExtra("user", user);
        startActivity(tmp);
    }

    /**
     * Switch to the StatisticsActivity view to see stats.
     */
    private void switchToStats()
    {
        Intent tmp = new Intent(this, StatisticsActivity.class);
        tmp.putExtra("user", user);
        startActivity(tmp);
    }

}