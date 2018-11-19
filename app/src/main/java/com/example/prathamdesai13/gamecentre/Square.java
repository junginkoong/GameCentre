package com.example.prathamdesai13.gamecentre;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Square extends GLSurfaceView {
    /**
     * Standard View constructor. In order to render something, you
     * must call {@link #setRenderer} to register a renderer.
     *
     * @param context
     * @param attrs
     */

    private FloatBuffer vertexBuffer;

    private final int BYTES_PER_FLOAT = 4;

    public Square(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLConfigChooser(8, 8, 8, 8, 0, 0);
        // define 4 triangles for chaining
//        float[] vertices = {
//                -0.5f, -0.5f, 0.0f,
//                0.5f, -0.5f, 0.0f,
//                -0.5f, 0.5f, 0.0f,
//                0.5f, 0.5f, 0.0f
//        };

        float [] vertices = {
                0.0f, 2.0f, 0.0f,
                3.5f, 0.0f, 0.0f,
                -3.5f, 0.0f, 0.0f,
                0.0f, -2.0f, 0.0f,
        };

        vertexBuffer = ByteBuffer.allocateDirect(vertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        setRenderer(new TileRenderer(vertexBuffer));

    }

    public void updateMap(){



    }
}
