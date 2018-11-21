package com.example.prathamdesai13.gamecentre;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class ZigZagRenderer implements GLSurfaceView.Renderer{

    private static final String TAG = "ZigZagRenderer";
    private static final int ARRAY_SIZE = 10;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private ArrayList<Square> mSquares = new ArrayList<>();

    private Resources res;

    public float[] coord;
    public int balance = 0;



    public ZigZagRenderer(Resources res){this.res =res;}


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        //Starting platform
        float[] check = {
                0.0f,  -0.25f, 0.0f, // a1 , a2
                -0.5f, -0.5f, 0.0f, // b1, b2
                0.0f, -0.75f, 0.0f, // c1 c2
                0.5f,  -0.5f, 0.0f }; //d1 d2
        float color[] = {0.5f, 0.5f, 0.5f, 1.0f};

        mSquares.add(new Square(check, color, res));

        //Start off with a tile on the right
        float[] check2 = {
                -0.5f/3f,(-0.25f - (2f*(-0.25f +0.5f)/3f -0.5f)) -0.25f, 0.0f,
                2f*-0.5f/3f, -0.25f, 0.0f,
                -0.5f/3f, 2f*(-0.25f +0.5f)/3f + (-0.5f), 0.0f,
                0.0f, -0.25f, 0.0f };
        float color2[] = {0.0f, 0.0f, 0.0f, 1.0f};
        mSquares.add(new Square(check2, color2, res));
        balance += 1; //added one tile to the right
        //save recent coordination
        coord = check2;

        //Now randomly generate a number between 0 and 1 (left and right). Unless out-of-bound.
        for (int i = 0; i < 12; i++){
            int rand = (int) (Math.random()*2); //this is random number
            boolean goRight = true;
            if (balance == 3){ goRight = false;} //no space in right
            else if (balance == -3){goRight = true;} // no space in left
            else if (rand == 0){goRight = true;} // generated right
            else if(rand == 1){goRight = false;} //generated left

            if (goRight){ //then draw a square right
                float[] temp_coord = {
                        coord[3], 2f*(coord[1] - coord[4]) + coord[4], 0.0f,
                        coord[0] - 2f*(coord[0] - coord[3]), coord[1], 0.0f,
                        coord[3], coord[4], 0.0f,
                        coord[0], coord[1], 0.0f};
                mSquares.add(new Square(temp_coord, color2, res));
                coord = temp_coord; //update
                balance += 1;
            } else if (!goRight){ //then draw a square left
                float[] temp_coord = {
                        coord[9], 2f*(coord[1] - coord[10]) + coord[10], 0.0f,
                        coord[0], coord[1], 0.0f,
                        coord[9], coord[10], 0.0f,
                        2f*(coord[9] - coord[0]) + coord[0], coord[1], 0.0f};
                mSquares.add(new Square(temp_coord, color2, res));
                coord = temp_coord; //update
                balance -= 1;
            }
        }


    }

    @Override
    public void onDrawFrame(GL10 unused) {

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        for (Square s: mSquares){
            s.draw(mMVPMatrix);
        }

    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }


    /**
     * Utility method for compiling a OpenGL shader.
     *
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}
