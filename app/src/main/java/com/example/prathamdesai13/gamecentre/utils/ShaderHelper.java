package com.example.prathamdesai13.gamecentre.utils;

import javax.net.ssl.SSLHandshakeException;

import static android.opengl.GLES20.*;


public class ShaderHelper {

    private static final String TAG = "ShaderHelper";

    public static int compilerVertexShader(String shaderCode){
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode){

        return compileShader(GL_FRAGMENT_SHADER, shaderCode);

    }

    // takes in source code for shader, and a shader type
    // if opengl successfully compiles the shader then it returns shader reference id, otherwise 0
    private static int compileShader(int type, String shaderCode){

        final int SHADER_OBJECT_ID = glCreateShader(type); // create new shader object and store id

        if (SHADER_OBJECT_ID == 0){
            System.out.println(TAG + ": Could not create new shader");
            return 0;
        }

        glShaderSource(SHADER_OBJECT_ID, shaderCode); // upload src code
        glCompileShader(SHADER_OBJECT_ID); // compiles shader object at reference id

        final int[] compileStatus = new int[1];
        // reads compile status of shader object at reference id, and writes it to 0th entry in compile status array
        glGetShaderiv(SHADER_OBJECT_ID, GL_COMPILE_STATUS, compileStatus, 0);

        if (compileStatus[0] == 0){ // compile failed
            // delete shader at reference id
            glDeleteShader(SHADER_OBJECT_ID);
            System.out.println(TAG + ": Could not compile shader");
            return 0;
        }

        return SHADER_OBJECT_ID;
    }
}
