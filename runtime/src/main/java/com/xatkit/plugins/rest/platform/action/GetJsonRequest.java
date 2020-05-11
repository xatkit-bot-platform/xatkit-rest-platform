package com.xatkit.plugins.rest.platform.action;

import com.google.gson.JsonElement;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.rest.platform.RestPlatform;
import com.xatkit.plugins.rest.platform.utils.ApiResponse;
import com.xatkit.plugins.rest.platform.utils.Helpers;
import fr.inria.atlanmod.commons.log.Log;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * A GET REST action for Json expected responses.
 * <p>
 * This action provides the execution logic to compute GET requests over a
 * provided endpoint. Request parameters and headers can be specified through
 * the constructor parameters.
 */
public class GetJsonRequest extends RestRequest<JsonElement, JsonElement> {

    /**
     * Constructs A Get action for Json expected response
     *
     * @param runtimePlatform the {@link RuntimePlatform} containing this action
     * @param session         the {@link XatkitSession} associated to this action
     * @param restEndpoint    the REST API endpoint to request
     * @param queryParams     the {@link Map} of query parameters to include in the request
     * @param pathParams      the {@link Map} of path parameters to include in the request
     * @param headers         the {@link Map} of user-defined headers to include in
     *                        the request
     */
    public GetJsonRequest(RestPlatform runtimePlatform, XatkitSession session, String restEndpoint,
                          Map<String, Object> queryParams, Map<String, String> pathParams,
                          Map<String, String> headers) {
        super(runtimePlatform, session, MethodKind.GET, restEndpoint, queryParams, pathParams, null, headers,
                Collections.emptyMap());
    }

    /**
     * Computes a Get JSON Action
     * retu
     */
    @Override
    public ApiResponse<JsonElement> compute() throws Exception {
        HttpRequest request = Unirest.get(restEndpoint);
        if (nonNull(headers) && !headers.isEmpty())
            request.headers(headers);
        if (nonNull(queryParameters) && !queryParameters.isEmpty())
            request.queryString(queryParameters);
        if (nonNull(pathParameters) && !pathParameters.isEmpty())
            pathParameters.forEach((k, v) -> request.routeParam(k, v));

        Log.info("Sent GET request on {0}", request.getUrl());

        HttpResponse<InputStream> response = request.asBinary();
        return handleResponse(response.getHeaders(), response.getStatus(), response.getStatusText(), response.getBody(),
                Helpers::parseJson);
    }
}
