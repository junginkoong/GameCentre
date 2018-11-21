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
    private static final int ARRAY_SIZE = 25;
    private static final float SPEED_TILE = 0.075f;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];

    private ArrayList<Square> mSquares = new ArrayList<>();
    private ArrayList<Integer> mItemIndex = new ArrayList<>();
    private ArrayList<Triangle> mItem = new ArrayList<>();

    private Resources res;

    public float[] coord;
    public int balance = 0;
    private float Tempy;
    private long time;
    private long time1;
    private float interval = 0.70f;

    private float color[] = {102f/255f, 178f/255f, 255f/255f, 1.0f};



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

        mSquares.add(new Square(check, color, res)); //Create starting platform
        mItemIndex.add(0); // starting platform does not have item

        //Start off with a tile on the right
        float[] check2 = {
                -0.5f/3f,(-0.25f - (2f*(-0.25f +0.5f)/3f -0.5f)) -0.25f, 0.0f,
                2f*-0.5f/3f, -0.25f, 0.0f,
                -0.5f/3f, 2f*(-0.25f +0.5f)/3f + (-0.5f), 0.0f,
                0.0f, -0.25f, 0.0f };
        mSquares.add(new Square(check2, color, res));
        balance += 1; //added one tile to the right
        mItemIndex.add(0); //Lets say first platform does not have item as well

        //save recent coordination
        coord = check2;

        // Initially randomly generate a number between 0 and 1 (left and right). Unless out-of-bound.
        for (int i = 0; i < ARRAY_SIZE; i++){
            generateTile();
        }
        //Set current system time when zigzag renderer is called
        time1 = System.currentTimeMillis();

    }

    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch;

        //Count the passing of time for system usage
        time = System.currentTimeMillis();

        //calculate the time passed since this renderer has been created
        Tempy = 0.0003f * ((int) time - (int) time1);

        //Set identity to model matrix
        Matrix.setIdentityM(mModelMatrix, 0);

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        Matrix.translateM(mModelMatrix, 0, 0, -Tempy, 0);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        //Clone and multiply the matrix
        scratch = mMVPMatrix.clone();
        Matrix.multiplyMM(mMVPMatrix, 0, scratch, 0, mModelMatrix, 0);

        for (Square s: mSquares){
            s.draw(mMVPMatrix);
        }
        for (Triangle t: mItem){
            t.draw(mMVPMatrix);
        }

        //check for interval and generate a new tile while deleting the old one
        if (Tempy > interval){
            generateTile(); //generate
            //remove the first tile after adding another
            mSquares.remove(0);
            if (mItemIndex.get(0) == 1) {
                for (int i = 0; i < 4; i++) {
                    mItem.remove(0);
                }
            }
            mItemIndex.remove(0);
            interval = Tempy + SPEED_TILE; //update next interval
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

    /**
     * Helper function for saving a new tile and deleting the last one
     *
     *
     *
     */
    public void generateTile(){
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
            mSquares.add(new Square(temp_coord, color, res));
            coord = temp_coord; //update
            balance += 1;
        } else if (!goRight){ //then draw a square left
            float[] temp_coord = {
                    coord[9], 2f*(coord[1] - coord[10]) + coord[10], 0.0f,
                    coord[0], coord[1], 0.0f,
                    coord[9], coord[10], 0.0f,
                    2f*(coord[9] - coord[0]) + coord[0], coord[1], 0.0f};
            mSquares.add(new Square(temp_coord, color, res));
            coord = temp_coord; //update
            balance -= 1;
        }
        determineItem(goRight);
    }

    /**
     * Determine if a particular tile has item associated with it
     */
    public boolean determineItem(boolean goRight){
        int rand = (int) (Math.random()*10); //random generation from 0 to 9
        float a1;
        float a2 = coord[4];
        if (rand == 4 ||  rand == 5){
            mItemIndex.add(1);
            if (goRight){
                a1 = (coord[3] - coord[9])/2 + coord[9];
            } else {
                a1 = (coord[9] - coord[3])/2 + coord[3];
            }
            generateItem(a1, a2);
        } else {
            mItemIndex.add(0);
        }
        return true;
    }

    /**
     * generate item and save it to mItem ArrayList
     *
     * @param a1 x-coordinate of centre
     * @param a2 y-coordinate of centre
     */
    public void generateItem(float a1, float a2){
        float temp_coords_1[] = {
                a1 - 0.005f, a2 + 0.07f, 0.0f,
                a1 + 0.055f, a2 +0.02f, 0.0f,
                a1, a2, 0.0f
        };
        float color_1[] = { 1.0f, 0.7f, 1.0f, 0.0f };
        mItem.add(new Triangle(temp_coords_1, color_1, res));

        float temp_coords_2[] = {
                a1 - 0.005f, a2 + 0.07f, 0.0f,
                a1 - 0.065f, a2 + 0.01f, 0.0f,
                a1, a2, 0.0f
        };
        float color_2[] = { 1.0f, 0.0f, 1.0f, 0.0f };

        mItem.add(new Triangle(temp_coords_2, color_2, res));

        float temp_coords_3[] = {
                a1, a2, 0.0f,
                a1 + 0.055f, a2 + 0.02f, 0.0f,
                a1, a2 - 0.035f, 0.0f
        };

        mItem.add(new Triangle(temp_coords_3, color_2, res));

        float temp_coords_4[] = {
                a1, a2, 0.0f,
                a1, a2 - 0.035f, 0.0f,
                a1 - 0.065f, a2 + 0.01f, 0.0f
        };

        float color_3[] = { 0.6f, 0.0f, 0.6f, 0.0f };

        mItem.add(new Triangle(temp_coords_4, color_3, res));
    }
}
