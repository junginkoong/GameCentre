package com.example.prathamdesai13.gamecentre;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class ZigZagDisplay extends Activity {

    private GLSurfaceView gls;
    private boolean renderSet = false; // remember if gls is in valid state or not
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gls = new GLSurfaceView(this);
        gls.setEGLContextClientVersion(2);
        gls.setRenderer(new TileRenderer(this, 0.0f, 0.0f));
        renderSet = true;
        setContentView(gls);

    }

    @Override
    protected void onPause(){
        super.onPause();
        if (renderSet)
            gls.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (renderSet)
            gls.onResume();
    }
}
