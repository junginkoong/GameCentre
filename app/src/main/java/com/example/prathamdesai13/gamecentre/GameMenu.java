package com.example.prathamdesai13.gamecentre;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameMenu extends Activity {
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        user = (String) b.get("USERNAME");
        setContentView(R.layout.activity_game_menu);
        addSlidingTilesGameButtonListener();
        addBlockyGameButtonListener();
        addFlowGameButtonListener();
    }

    private void addSlidingTilesGameButtonListener(){

        Button game = findViewById(R.id.SlidingTileGame);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("IT GOT HERE");
                startSlidingTiles();
            }
        });
    }

    private void addFlowGameButtonListener(){

        Button game = findViewById(R.id.FlowGame);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // does nothing rn
            }
        });
    }

    private void addBlockyGameButtonListener(){

        Button game = findViewById(R.id.BlockyGame);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // does nothing rn
            }
        });
    }

    private void startSlidingTiles(){
        Intent tmp = new Intent(this, SlidingTiles.class);
        tmp.putExtra("USERNAME", user);
        startActivity(tmp);
    }


}
