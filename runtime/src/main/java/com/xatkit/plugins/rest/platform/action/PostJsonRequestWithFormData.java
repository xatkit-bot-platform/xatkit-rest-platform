package com.xatkit.plugins.rest.platform.action;


import com.google.gson.JsonElement;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.rest.platform.RestPlatform;
import com.xatkit.plugins.rest.platform.utils.ApiResponse;
import com.xatkit.plugins.rest.platform.utils.Helpers;
import fr.inria.atlanmod.commons.log.Log;

import java.io.InputStream;
import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * A POST REST action with form data.
 * <p>
 * This action provides the execution logic to compute POST requests over a
 * provided endpoint. Request parameters and headers, and form data can be specified through
 * the constructor parameters.
 */
public class PostJsonRequestWithFormData extends RestRequest<JsonElement, JsonElement> {

    /**
     * Constructs a POST Json request with form data
     *
     * @param runtimePlatform the {@link RuntimePlatform} containing this action
     * @param session         the {@link XatkitSession} associated to this action
     * @param restEndpoint    the REST API endpoint to request
     * @param queryParams     the {@link Map} of query parameters to include in the request
     * @param pathParams      the {@link Map} of path parameters to include in the request
     * @param headers         the {@link Map} of user-defined headers to include in
     *                        the request
     * @param formParams      the {@link Map} of form parameters to include in the request
     */
    public PostJsonRequestWithFormData(RestPlatform runtimePlatform, XatkitSession session, String restEndpoint,
                                       Map<String, Object> queryParams, Map<String, String> pathParams, Map<String,
            String> headers, Map<String, Object> formParams) {
        super(runtimePlatform, session, MethodKind.POST, restEndpoint, queryParams, pathParams, null, headers,
                formParams);

    }

    /**
     * Computes the request
     *
     * @return an instance of {@link ApiResponse} containing the response of the request
     */
    @Override
    protected final ApiResponse<JsonElement> compute() throws Exception {
        HttpRequest request = Unirest.post(restEndpoint);
        if (nonNull(headers) && !headers.isEmpty())
            request.headers(headers);
        if (nonNull(queryParameters) && !queryParameters.isEmpty())
            request.queryString(queryParameters);
        if (nonNull(pathParameters) && !pathParameters.isEmpty())
            pathParameters.forEach((k, v) -> request.routeParam(k, v.toString()));
        if (nonNull(formParameters) && !formParameters.isEmpty())
            ((HttpRequestWithBody) request).fields(formParameters);
        if (nonNull(requestBody))
            ((HttpRequestWithBody) request).body(requestBody.toString());

        Log.info("Sent POST query on {0}", request.getUrl());

        HttpResponse<InputStream> response = request.asBinary();
        return handleResponse(response.getHeaders(), response.getStatus(), response.getStatusText(),
                response.getBody(), Helpers::parseJson);
    }
}
