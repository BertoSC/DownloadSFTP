package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class ImageDownloader  {

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(ImageDeserializer.class, Download.class)
            .setPrettyPrinting()
            .create();



    URL url = null;
    url = new URL(String link);
    InputStream in = url.openStream();
    File outputFile = new File(dasdasda);
    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
    }

}
