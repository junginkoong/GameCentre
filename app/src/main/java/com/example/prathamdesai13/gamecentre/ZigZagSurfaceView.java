package com.example.prathamdesai13.gamecentre;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class ZigZagSurfaceView extends GLSurfaceView {

    private final ZigZagRenderer mRenderer;

    public ZigZagSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
        //fix for error No Config chosen, but I don't know what this does.
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new ZigZagRenderer(getResources());
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
