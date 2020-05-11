package com.xatkit.plugins.rest.platform.action;

import com.mashape.unirest.http.Headers;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.rest.platform.RestPlatform;
import com.xatkit.plugins.rest.platform.utils.ApiResponse;
import com.xatkit.plugins.rest.platform.utils.ResponseHandler;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static fr.inria.atlanmod.commons.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

/**
 * A generic REST action.
 * <p>
 * This action provides the execution logic to compute HTTP requests over a provided endpoint. Request
 * parameters, headers, and body can be specified through the constructor parameters.
 *
 * @param <E> the request payload type
 * @param <T> the response payload type
 */
public abstract class RestRequest<E, T> extends RuntimeAction<RestPlatform> {


    /**
     * The {@link MethodKind} of the request to perform.
     */
    protected MethodKind method;

    /**
     * A {@link Map} containing user-defined headers to include in the request.
     * <p>
     * This {@link Map} is filled with the following template: {@code header_name -> header_value}.
     */
    protected Map<String, String> headers;

    /**
     * The REST API endpoint to request.
     * <p>
     * The provided REST endpoint url must be <b>absolute</b>. The url can include path templates.
     */
    protected String restEndpoint;

    /**
     * A {@link Map} containing query parameters to include in the request.
     * <p>
     * This {@link Map} is filled with the following template {@code param_name -> param_value}. Note that the
     * {@link Map}'s values are {@link Object}s in order to support multipart body.
     */
    protected Map<String, Object> queryParameters;


    /**
     * A {@link Map} containing path parameters to include in the request.
     * <p>
     * This {@link Map} is filled with the following template {@code param_name -> param_value}.
     */
    protected Map<String, String> pathParameters;


    /**
     * A {@link Map} containing form data parameters to include in the request.
     * <p>
     * This {@link Map} is filled with the following template {@code param_name -> param_value}.
     */
    protected Map<String, Object> formParameters;

    /**
     * The object to include in the request's body.
     */
    protected E requestBody;

    /**
     * Constructs a new {@link RestRequest}.
     * <p>
     * This method doesn't perform the REST API request, this is done asynchronously in the {@link #compute()} method.
     *
     * @param runtimePlatform the {@link RuntimePlatform} containing this action
     * @param session         the {@link XatkitSession} associated to this action
     * @param method          the REST method to use
     * @param restEndpoint    the REST API endpoint to request
     * @param queryParameters the {@link Map} of query parameters to include in the request
     * @param pathParameters  the {@link Map} of path parameters to include in the request
     * @param requestBody     the content of the payload of the request
     * @param headers         the {@link Map} of user-defined headers to include in the request
     * @param formParameters  the {@link Map} of the form parameters to include in the request
     * @throws NullPointerException     if the provided {@code runtimePlatform} or {@code session} is {@code null}
     * @throws IllegalArgumentException if the provided {@code method} is {@code null}, or if the provided {@code
     *                                  restEndpoint} is {@code null} or {@code empty}
     */
    public RestRequest(RestPlatform runtimePlatform, XatkitSession session, MethodKind method,
                       String restEndpoint,
                       @Nullable Map<String, Object> queryParameters,
                       @Nullable Map<String, String> pathParameters,
                       @Nullable E requestBody,
                       @Nullable Map<String, String> headers,
                       @Nullable Map<String, Object> formParameters
    ) {
        super(runtimePlatform, session);
        checkArgument(nonNull(method), "Cannot construct a %s action with the provided method %s",
                this.getClass().getSimpleName(), method);
        checkArgument(nonNull(restEndpoint) && !restEndpoint.isEmpty(), "Cannot construct a %s action with the " +
                "provided REST endpoint %s", this.getClass().getSimpleName(), restEndpoint);

        this.method = method;
        this.headers = headers;
        this.restEndpoint = restEndpoint;
        this.queryParameters = queryParameters;
        this.pathParameters = pathParameters;
        this.formParameters = formParameters;
        this.requestBody = requestBody;
        addDefaultParameters();
    }

    /**
     * Adds default parameters from the configuration file
     */
    private void addDefaultParameters() {
        Map<String, Object> queryParameters = runtimePlatform.getConfiguration().getDefaultQueryParameters();
        if (nonNull(queryParameters) && !queryParameters.isEmpty()) {
            queryParameters.forEach((k, v) -> this.queryParameters.putIfAbsent(k, v));
        }
        Map<String, String> defaultHeaders = runtimePlatform.getConfiguration().getDefaultHeaders();
        if (nonNull(defaultHeaders) && !defaultHeaders.isEmpty()) {
            defaultHeaders.forEach((k, v) -> this.headers.putIfAbsent(k, v));
        }
    }

    /**
     * constructs an {@link ApiResponse}} from the response of the HTTP request
     *
     * @param headers         the headers returned as part of the response
     * @param statusCode      the status code of the response
     * @param statusText      the status text of the response
     * @param body            the body of the response
     * @param responseHandler a {@link FunctionalInterface} to handle the payload of the response
     * @return an {@link ApiResponse} instance
     */
    protected ApiResponse<T> handleResponse(Headers headers, int statusCode, String statusText,
                                            InputStream body, ResponseHandler<T> responseHandler) {
        ApiResponse<T> apiResponse = new ApiResponse<T>();
        apiResponse.setBody(responseHandler.handleResponse(body));
        apiResponse.setHeaders(((Map<String, List<String>>) headers));
        apiResponse.setStatus(statusCode);
        apiResponse.setStatusText(statusText);

        return apiResponse;
    }

    /**
     * The kind of REST methods supported by this class.
     */
    public enum MethodKind {
        GET,
        POST,
        PUT,
        DELETE
    }
}
