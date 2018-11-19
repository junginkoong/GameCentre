package com.example.prathamdesai13.gamecentre;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TileRenderer implements GLSurfaceView.Renderer {

    private FloatBuffer vertexBuffer;
    private float w;
    private float h;
    private float screenW;
    private float screenH;
    public TileRenderer(FloatBuffer vertexBuffer){

//        this.context = context;
//        // define tile metrics
//        this.width = 10.0f;
//        this.length = 10.0f;
//
//        // define vertices of the tile
//        // note openGL renders points, lines, and triangles
//        vertices = new float[8];
//        vertices[0] = x;
//        vertices[1] = y;
//        vertices[2] = x + width;
//        vertices[3] = y;
//        vertices[4] = x;
//        vertices[5] = y + length;
//        vertices[6] = x + width;
//        vertices[7] = y + length;
//
//        // mem not managed by garbage colelctor
//        // allocate mem block directly, order bytes in native order, convert to float buffer
//        nativeVertices = ByteBuffer.allocateDirect(vertices.length * BYTES_PER_FLOAT)
//                .order(ByteOrder.nativeOrder()).asFloatBuffer();
//
//        nativeVertices.put(vertices);

        this.vertexBuffer = vertexBuffer;

    }
    /**
     * Called when the surface is created or recreated.
     * <p>
     * Called when the rendering thread
     * starts and whenever the EGL context is lost. The EGL context will typically
     * be lost when the Android device awakes after going to sleep.
     * <p>
     * Since this method is called at the beginning of rendering, as well as
     * every time the EGL context is lost, this method is a convenient place to put
     * code to create resources that need to be created when the rendering
     * starts, and that need to be recreated when the EGL context is lost.
     * Textures are an example of a resource that you might want to create
     * here.
     * <p>
     * Note that when the EGL context is lost, all OpenGL resources associated
     * with that context will be automatically deleted. You do not need to call
     * the corresponding "glDelete" methods such as glDeleteTextures to
     * manually delete these lost resources.
     * <p>
     *
     * @param gl     the GL interface. Use <code>instanceof</code> to
     *               test if the interface supports GL11 or higher interfaces.
     * @param config the EGLConfig of the created surface. Can be used
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_ALPHA_TEST);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);


        // We are in 2D, so no need depth
        gl.glDisable(GL10.GL_DEPTH_TEST);
        // Enable vertex arrays (we'll use them to draw primitives).
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

    }

    /**
     * Called when the surface changed size.
     * <p>
     * Called after the surface is created and whenever
     * the OpenGL ES surface size changes.
     * <p>
     * Typically you will set your viewport here. If your camera
     * is fixed then you could also set your projection matrix here:
     * <pre class="prettyprint">
     * void onSurfaceChanged(GL10 gl, int width, int height) {
     *     gl.glViewport(0, 0, width, height);
     *     // for a fixed camera, set the projection too
     *     float ratio = (float) width / height;
     *     gl.glMatrixMode(GL10.GL_PROJECTION);
     *     gl.glLoadIdentity();
     *     gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
     * }
     * </pre>
     *
     * @param gl     the GL interface. Use <code>instanceof</code> to
     *               test if the interface supports GL11 or higher interfaces.
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        if (width > height){
            h = 800;
            w = width * h / height;
        }else{
            w = 800;
            h = height * w / width;
        }

        screenW = width;
        screenH = height;

        gl.glViewport(0, 0, (int) screenW, (int) screenH);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, w, h, 0, -1, 1);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /**
     * Called to draw the current frame.
     * <p>
     * This method is responsible for drawing the current frame.
     * <p>
     * The implementation of this method typically looks like this:
     * <pre class="prettyprint">
     * void onDrawFrame(GL10 gl) {
     *     gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
     *     //... other gl calls to render the scene ...
     * }
     * </pre>
     *
     * @param gl the GL interface. Use <code>instanceof</code> to
     *           test if the interface supports GL11 or higher interfaces.
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glPushMatrix();
        gl.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        gl.glTranslatef( w / 2, h / 2, 0.0f);
        gl.glScalef(120, 100, 0);
        gl.glColor4f((float) (110.0 / 255.0), (float) (189.0 / 255.0), (float) (250.0 / 255.0), 1.0f);
        gl.glVertexPointer(3, GLES20.GL_FLOAT, 0, this.vertexBuffer);
        gl.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        gl.glPopMatrix();
    }
}
