package com.example.prathamdesai13.gamecentre.utils;

import android.content.Context;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceReader {

    public static String readFileFromResource(Context context, int resourceId) {

        StringBuilder builder = new StringBuilder();

        try {
            InputStream stream = context.getResources().openRawResource(resourceId);
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                builder.append(nextLine);
                builder.append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not open resource: " + resourceId, e);
        } catch (Resources.NotFoundException nfe) {
            throw new RuntimeException("Resource not found: " + resourceId, nfe);
        }
        return builder.toString();
    }
}
