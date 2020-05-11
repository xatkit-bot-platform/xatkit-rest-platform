package com.xatkit.plugins.rest.platform.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import fr.inria.atlanmod.commons.log.Log;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Provides general helpers
 */
public class Helpers {



    /**
     * Transforms an {@link InputStream} instance to a {@link JsonElement} object
     *
     * @param inputStream
     * @return a {@link JsonElement} instance
     */
    public static JsonElement parseJson(InputStream inputStream) {
        JsonElement jsonResponse = null;
        try {
            jsonResponse = new JsonParser().parse(new InputStreamReader(inputStream));
        } catch (JsonParseException e) {
            Log.error(e, "Can't parse the response. See the attached exception");
        }

        return jsonResponse;
    }

}
