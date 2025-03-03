package org.example;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImageDeserializer implements JsonDeserializer<List<Download>> {
    @Override
    public List<Download> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonArray ja = jsonElement.getAsJsonArray();
        List<Download> urlList = new ArrayList<>();
        for (JsonElement je: ja) {
            JsonObject jo = je.getAsJsonObject();
            String url = jo.get("download_url").getAsString();
            Download download = new Download(url);
            urlList.add(download);
        }
        return urlList;
    }
}