package com.xatkit.plugins.rest.platform.action;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.xatkit.execution.StateContext;
import com.xatkit.plugins.rest.platform.RestPlatform;
import fr.inria.atlanmod.commons.log.Log;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public abstract class JsonRestRequest<E> extends RestRequest<E, JsonElement> {

    public JsonRestRequest(@NonNull RestPlatform platform, @NonNull StateContext context,
                           @NonNull MethodKind method,
                           @NonNull String restEndpoint,
                           @Nullable Map<String, Object> queryParameters,
                           @Nullable Map<String, String> pathParameters,
                           @Nullable E requestBody,
                           @Nullable Map<String, String> headers,
                           @Nullable Map<String, Object> formParameters
    ) {
        super(platform, context, method, restEndpoint, queryParameters, pathParameters, requestBody, headers,
                formParameters);
    }

    /**
     * Transforms an {@link InputStream} instance to a {@link JsonElement} object
     *
     * @param body
     * @return a {@link JsonElement} instance
     */
    @Override
    protected JsonElement parseResponseBody(InputStream body) {
        JsonElement jsonResponse = null;
        try {
            jsonResponse = new JsonParser().parse(new InputStreamReader(body));
        } catch (JsonParseException e) {
            Log.error(e, "Can't parse the response. See the attached exception");
        }

        return jsonResponse;
    }
}
