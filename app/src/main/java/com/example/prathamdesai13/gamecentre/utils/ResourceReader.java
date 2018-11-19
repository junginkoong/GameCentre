package com.example.prathamdesai13.gamecentre.utils;

import android.content.res.Resources;
<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.IOException;
=======

>>>>>>> a3d951609f6f32faa5f09e8051207e48f98ec4fc
import java.io.InputStream;
import java.util.Scanner;

public class ResourceReader {

    public static String readFileFromResource(Resources res, int resourceId) {
        InputStream input_vertex = res.openRawResource(resourceId);
        Scanner vertex = new Scanner(input_vertex).useDelimiter("\\A");
        String shader = vertex.hasNext() ? vertex.next() : "";
        return shader;
    }
}
