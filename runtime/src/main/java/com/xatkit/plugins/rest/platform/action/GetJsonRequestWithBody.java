package com.xatkit.plugins.rest.platform.action;


import com.google.gson.JsonElement;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.rest.platform.RestPlatform;

import java.util.Collections;
import java.util.Map;

import static java.util.Objects.nonNull;

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
     * @param runtimePlatform the {@link RuntimePlatform} containing this action
     * @param session         the {@link XatkitSession} associated to this action
     * @param restEndpoint    the REST API endpoint to request
     * @param requestBody     the body of the request
     * @param queryParams     the {@link Map} of query parameters to include in the request
     * @param pathParams      the {@link Map} of path parameters to include in the request
     * @param headers         the {@link Map} of user-defined headers to include in
     *                        the request
     */
    public GetJsonRequestWithBody(RestPlatform runtimePlatform, XatkitSession session, String restEndpoint,
                                  Map<String, Object> queryParams, Map<String, String> pathParams,
                                  JsonElement requestBody, Map<String, String> headers) {
        super(runtimePlatform, session, MethodKind.GET, restEndpoint, queryParams, pathParams, requestBody, headers,
                Collections.emptyMap());
    }

    @Override
    protected HttpRequest buildRequest() {
        HttpRequest request = Unirest.get(this.restEndpoint);

        request.headers(this.headers);
        request.queryString(this.queryParameters);
        this.pathParameters.forEach(request::routeParam);
        if(nonNull(this.requestBody)) {
            ((HttpRequestWithBody) request).body(this.requestBody.toString());
        }
        return request;
    }
}
