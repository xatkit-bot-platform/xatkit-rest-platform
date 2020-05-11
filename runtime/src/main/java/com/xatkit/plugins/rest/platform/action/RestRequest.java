package com.xatkit.plugins.rest.platform.action;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.request.HttpRequest;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.rest.platform.RestPlatform;
import com.xatkit.plugins.rest.platform.utils.ApiResponse;
import fr.inria.atlanmod.commons.log.Log;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static fr.inria.atlanmod.commons.Preconditions.checkArgument;
import static java.util.Objects.isNull;
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
    @NonNull
    protected Map<String, Object> queryParameters;

    /**
     * A {@link Map} containing path parameters to include in the request.
     * <p>
     * This {@link Map} is filled with the following template {@code param_name -> param_value}.
     */
    @NonNull
    protected Map<String, String> pathParameters;

    /**
     * The object to include in the request's body.
     */
    @Nullable
    protected E requestBody;

    /**
     * A {@link Map} containing user-defined headers to include in the request.
     * <p>
     * This {@link Map} is filled with the following template: {@code header_name -> header_value}.
     */
    @NonNull
    protected Map<String, String> headers;

    /**
     * A {@link Map} containing form data parameters to include in the request.
     * <p>
     * This {@link Map} is filled with the following template {@code param_name -> param_value}.
     */
    @NonNull
    protected Map<String, Object> formParameters;

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
    public RestRequest(@NonNull RestPlatform runtimePlatform, @NonNull XatkitSession session,
                       @NonNull MethodKind method,
                       @NonNull String restEndpoint,
                       @Nullable Map<String, Object> queryParameters,
                       @Nullable Map<String, String> pathParameters,
                       @Nullable E requestBody,
                       @Nullable Map<String, String> headers,
                       @Nullable Map<String, Object> formParameters
    ) {
        super(runtimePlatform, session);
        checkArgument(!restEndpoint.isEmpty(), "Cannot create a {0} for the following endpoint: {1}",
                this.getClass().getSimpleName(), restEndpoint);
        this.method = method;
        this.restEndpoint = restEndpoint;
        this.queryParameters = isNull(queryParameters) ? new HashMap<>() : queryParameters;
        this.addDefaultQueryParameters(this.queryParameters);
        this.pathParameters = isNull(pathParameters) ? new HashMap<>() : pathParameters;
        /*
         * No need to deal with isNull(requestBody), the body attribute is @Nullable.
         */
        this.requestBody = requestBody;
        this.headers = isNull(headers) ? new HashMap<>() : headers;
        this.addDefaultHeaders(headers);
        this.formParameters = isNull(formParameters) ? new HashMap<>() : formParameters;
    }

    /**
     * Adds the default query parameters to the provided {@code baseParameters}.
     * <p>
     * The default query parameters are specified with the configuration key
     * {@link com.xatkit.plugins.rest.platform.RestPlatformConfiguration#DEFAULT_QUERY_PARAMETERS_PROPERTY}.
     *
     * @param baseParameters the {@link Map} containing the query parameters to update
     * @see com.xatkit.plugins.rest.platform.RestPlatformConfiguration#DEFAULT_QUERY_PARAMETERS_PROPERTY
     */
    private void addDefaultQueryParameters(Map<String, Object> baseParameters) {
        Map<String, Object> defaultQueryParameters =
                runtimePlatform.getRestPlatformConfiguration().getDefaultQueryParameters();
        if (nonNull(defaultQueryParameters) && !defaultQueryParameters.isEmpty()) {
            defaultQueryParameters.forEach(baseParameters::putIfAbsent);
        }
    }

    /**
     * Adds the default headers to the provided {@code baseHeaders}.
     * <p>
     * The default headers are specified with the configuration key
     * {@link com.xatkit.plugins.rest.platform.RestPlatformConfiguration#DEFAULT_HEADERS_PROPERTY}.
     *
     * @param baseHeaders the {@link Map} containing the headers to update
     * @see com.xatkit.plugins.rest.platform.RestPlatformConfiguration#DEFAULT_HEADERS_PROPERTY
     */
    private void addDefaultHeaders(Map<String, String> baseHeaders) {
        Map<String, String> defaultHeaders = runtimePlatform.getRestPlatformConfiguration().getDefaultHeaders();
        if (nonNull(defaultHeaders) && !defaultHeaders.isEmpty()) {
            defaultHeaders.forEach(baseHeaders::putIfAbsent);
        }
    }

    @Override
    protected final ApiResponse<T> compute() throws Exception {
        HttpRequest request = this.buildRequest();
        Log.debug("Sent {0} request on {1}", request.getHttpMethod().name(), request.getUrl());
        HttpResponse<InputStream> response = request.asBinary();
        return handleResponse(response);
    }

    /**
     * Creates an {@link ApiResponse} object wrapping the raw {@link HttpResponse}.
     *
     * @param response the {@link HttpResponse} to handle
     * @return the built {@link ApiResponse}
     * @see #parseResponseBody(InputStream)
     */
    protected ApiResponse<T> handleResponse(HttpResponse<InputStream> response) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setBody(parseResponseBody(response.getBody()));
        apiResponse.setHeaders(response.getHeaders());
        apiResponse.setStatus(response.getStatus());
        apiResponse.setStatusText(response.getStatusText());
        return apiResponse;
    }

    /**
     * Build the {@link HttpRequest} to send to the provided {@code restEndpoint}.
     * <p>
     * This method is implemented by concrete subclasses that take care of creating the correct {@link HttpRequest}
     * type and set its parameters.
     *
     * @return the {@link HttpRequest} to send to the provided {@code restEndpoint}
     */
    protected abstract HttpRequest buildRequest();

    /**
     * Parses the {@link HttpResponse}'s body into the return type of this {@link RestRequest}.
     *
     * @param body the {@link InputStream} containing the {@link HttpResponse}'s body
     * @return the parsed body
     */
    protected abstract T parseResponseBody(InputStream body);

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
