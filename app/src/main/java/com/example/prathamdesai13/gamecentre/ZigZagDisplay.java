package com.example.prathamdesai13.gamecentre;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class ZigZagDisplay extends Activity {

    private GLSurfaceView gls;
    private GLSurfaceView gls1;
    private boolean renderSet = false; // remember if gls is in valid state or not
    private Square tile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zigzag_display);
        tile = (Square) findViewById(R.id.map);
//        gls1 = new GLSurfaceView(this);
//        gls1.setEGLContextClientVersion(2);
//        gls1.setRenderer(new PlayerRenderer());
//        setContentView(gls1);
//        renderSet = true;

    }

    @Override
    protected void onPause(){
        super.onPause();
        if (renderSet)
            gls1.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (renderSet)
            gls1.onResume();
    }
}
