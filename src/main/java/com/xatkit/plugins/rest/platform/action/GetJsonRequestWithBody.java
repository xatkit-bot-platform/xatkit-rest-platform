package com.xatkit.plugins.rest.platform.action;


import com.google.gson.JsonElement;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.execution.StateContext;
import com.xatkit.plugins.rest.platform.RestPlatform;

import java.util.Collections;
import java.util.Map;

/**
 * A GET REST action with body parameter for Json payloads.
 * <p>
 * This action provides the execution logic to compute GET requests over a
 * provided endpoint. Request parameters and headers, and body can be specified through
 * the constructor parameters.
 */
public class GetJsonRequestWithBody extends JsonRestRequest<JsonElement> {

    /**
     * Constructs a GET Json request with body parameter
     *
     * @param platform the {@link RuntimePlatform} containing this action
     * @param context         the {@link StateContext} associated to this action
     * @param restEndpoint    the REST API endpoint to request
     * @param requestBody     the body of the request
     * @param queryParams     the {@link Map} of query parameters to include in the request
     * @param pathParams      the {@link Map} of path parameters to include in the request
     * @param headers         the {@link Map} of user-defined headers to include in
     *                        the request
     */
    public GetJsonRequestWithBody(RestPlatform platform, StateContext context, String restEndpoint,
                                  Map<String, Object> queryParams, Map<String, String> pathParams,
                                  JsonElement requestBody, Map<String, String> headers) {
        super(platform, context, MethodKind.GET, restEndpoint, queryParams, pathParams, requestBody, headers,
                Collections.emptyMap());
    }

    @Override
    protected HttpRequest buildRequest() {
        HttpRequest request = Unirest.get(this.restEndpoint);

        request.headers(this.headers);
        request.queryString(this.queryParameters);
        this.pathParameters.forEach(request::routeParam);
        /*
         * We cannot set the body here, the type of request is GetRequest and it doesn't extend HttpRequestWithBody.
         */
        return request;
    }
}
