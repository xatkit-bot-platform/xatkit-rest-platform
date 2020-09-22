package com.xatkit.plugins.rest.platform.action;


import com.google.gson.JsonElement;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.execution.StateContext;
import com.xatkit.plugins.rest.platform.RestPlatform;

import java.util.Collections;
import java.util.Map;

/**
 * A DELETE REST action.
 * <p>
 * This action provides the execution logic to compute DELETE requests over a
 * provided endpoint. Request parameters and headers can be specified through
 * the constructor parameters.
 */
public class DeleteJsonRequest extends JsonRestRequest<JsonElement> {

    /**
     * Constructs A DELETE action for Json expected response
     *
     * @param platform the {@link RuntimePlatform} containing this action
     * @param context         the {@link StateContext} associated to this action
     * @param restEndpoint    the REST API endpoint to request
     * @param queryParams     the {@link Map} of query parameters to include in the request
     * @param pathParams      the {@link Map} of path parameters to include in the request
     * @param headers         the {@link Map} of user-defined headers to include in
     *                        the request
     */
    public DeleteJsonRequest(RestPlatform platform, StateContext context, String restEndpoint, Map<String,
            Object> queryParams, Map<String, String> pathParams, Map<String, String> headers) {
        super(platform, context, MethodKind.DELETE, restEndpoint, queryParams, pathParams, null, headers,
                Collections.emptyMap());
    }

    @Override
    protected HttpRequest buildRequest() {
        HttpRequestWithBody request = Unirest.delete(this.restEndpoint);
        request.headers(this.headers);
        request.queryString(this.queryParameters);
        this.pathParameters.forEach(request::routeParam);
        return request;
    }
}
