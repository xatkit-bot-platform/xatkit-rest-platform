package com.xatkit.plugins.rest.platform.action;

import com.google.gson.JsonElement;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.platform.action.RuntimeAction;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.rest.platform.RestPlatform;

import javax.annotation.Nullable;
import java.util.Map;

import static fr.inria.atlanmod.commons.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

/**
 * A generic REST action.
 * <p>
 * This action provides the execution logic to compute GET and POST REST requests over a provided endpoint. Request
 * parameters, headers, and body can be specified through the constructor parameters.
 * <p>
 * <b>Note</b>: this class assumes that the requested REST API expects nothing or JSON in the query body. This class
 * also expects that the API response contains an empty body or a {@link JsonElement}.
 *
 * @param <T> the {@link RuntimePlatform} subclass containing this {@link RestAction}
 * @param <E>
 */
public abstract class RestRequest<E> extends RuntimeAction<RestPlatform> {


    

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
     * This {@link Map} is filled with the following template {@code param_name -> param_value}. Note that the
     * {@link Map}'s values are {@link Object}s in order to support multipart body.
     */
    protected Map<String, Object> pathParameters;
    
    
    
    protected Map<String, Object> formParameters;

    /**
     * The {@link JsonElement} to include in the request's body.
     */
    protected E requestBody;

    /**
     * Constructs a new {@link RestAction}.
     * <p>
     * This method doesn't perform the REST API request, this is done asynchronously in the {@link #compute()} method.
     *
     * @param runtimePlatform the {@link RuntimePlatform} containing this action
     * @param session         the {@link XatkitSession} associated to this action
     * @param method          the REST method to use
     * @param restEndpoint    the REST API endpoint to request
     * @param requestParameters	the {@link Map} of query parameters to include in the request
     * @param headers         the {@link Map} of user-defined headers to include in the request
     * @param jsonContent     the {@link JsonElement} to include in the request's body
     * @throws NullPointerException     if the provided {@code runtimePlatform} or {@code session} is {@code null}
     * @throws IllegalArgumentException if the provided {@code method} is {@code null}, or if the provided {@code
     *                                  restEndpoint} is {@code null} or {@code empty}
     */
    public RestRequest(RestPlatform runtimePlatform, XatkitSession session, MethodKind method,
                      String restEndpoint,
                      @Nullable Map<String, Object> queryParameters,
                      @Nullable Map<String,Object> pathParameters,
                      @Nullable E requestBody,
                      @Nullable Map<String, String> headers,
                      @Nullable Map<String,Object> formParameters
                     ) {
        super(runtimePlatform, session);
        checkArgument(nonNull(method), "Cannot construct a %s action with the provided method %s", this.getClass().getSimpleName(), method);
        checkArgument(nonNull(restEndpoint) && !restEndpoint.isEmpty(), "Cannot construct a %s action with the " +
                "provided REST endpoint %s", this.getClass().getSimpleName(), restEndpoint);

        this.method = method;
        this.headers = headers;
        this.restEndpoint = restEndpoint;
        this.queryParameters = queryParameters;
        this.formParameters = formParameters;
        this.requestBody = requestBody;
        addDefaultParameters();
    }

    private void addDefaultParameters() {
    	Map<String, Object> queryParameters = runtimePlatform.getDefaultQueryParameters();
    	if(nonNull(queryParameters) && !queryParameters.isEmpty()) {
    		queryParameters.forEach((k,v)-> this.queryParameters.putIfAbsent(k, v));
    	}
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
