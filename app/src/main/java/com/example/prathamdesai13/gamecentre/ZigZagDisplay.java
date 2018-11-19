package com.example.prathamdesai13.gamecentre;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import java.io.InputStream;

public class ZigZagDisplay extends Activity {

    private GLSurfaceView gls;
    private GLSurfaceView gls1;
    private boolean renderSet = false; // remember if gls is in valid state or not
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gls = new GLSurfaceView(this);
        //gls.setEGLContextClientVersion(2);
        //gls.setRenderer(new TileRenderer(this, 0.0f, 0.0f));
        //setContentView(gls);

        Resources res = getResources();
        gls1 = new GLSurfaceView(this);
        gls1.setEGLContextClientVersion(2);
        gls1.setRenderer(new ItemRenderer(res));
        setContentView(gls1);

        renderSet = true;

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
