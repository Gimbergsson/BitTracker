package com.free.dennisg.bittrackr.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Dennis Gimbergsson on 2016-11-14.
 **/

public class MyDeserializer<T> implements JsonDeserializer<T>
{
    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        // Get the "content" element from the parsed JSON
        JsonElement content = je.getAsJsonObject().get("prev_out");
        GsonBuilder builder = new GsonBuilder();

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return builder.create().fromJson(content, type);

    }
}