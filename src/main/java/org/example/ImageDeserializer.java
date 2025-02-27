package org.example;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ImageDeserializer implements JsonDeserializer<Download> {
    @Override
    public Download deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jo = jsonElement.getAsJsonObject();
        String url = jo.get("download_url").getAsString();
        Download download = new Download(url);
        return download;
    }
}
