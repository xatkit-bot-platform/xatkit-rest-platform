package com.xatkit.plugins.rest.platform.action;


import com.google.gson.JsonElement;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.execution.StateContext;
import com.xatkit.plugins.rest.platform.RestPlatform;

import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * A GET REST action with form data for Json payloads.
 * <p>
 * This action provides the execution logic to compute GET requests over a
 * provided endpoint. Request parameters and headers, and form data can be specified through
 * the constructor parameters.
 */
public class GetJsonRequestWithFormData extends JsonRestRequest<JsonElement> {

    /**
     * Constructs a GET Json request with form data
     *
     * @param platform the {@link RuntimePlatform} containing this action
     * @param context         the {@link StateContext} associated to this action
     * @param restEndpoint    the REST API endpoint to request
     * @param queryParams     the {@link Map} of query parameters to include in the request
     * @param pathParams      the {@link Map} of path parameters to include in the request
     * @param headers         the {@link Map} of user-defined headers to include in
     *                        the request
     * @param formParams      the {@link Map} of form parameters to include in the request
     */
    public GetJsonRequestWithFormData(RestPlatform platform, StateContext context, String restEndpoint,
                                      Map<String, Object> queryParams, Map<String, String> pathParams, Map<String,
            String> headers, Map<String, Object> formParams) {
        super(platform, context, MethodKind.GET, restEndpoint, queryParams, pathParams, null, headers,
                formParams);
    }

    @Override
    protected HttpRequest buildRequest() {
        HttpRequest request = Unirest.get(this.restEndpoint);
        request.headers(this.headers);
        request.queryString(this.queryParameters);
        this.pathParameters.forEach(request::routeParam);
        ((HttpRequestWithBody) request).fields(this.formParameters);
        if(nonNull(this.requestBody)) {
            ((HttpRequestWithBody) request).body(requestBody.toString());
        }
        return request;
    }
}
