package com.xatkit.plugins.rest.platform.action;



import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.rest.platform.utils.ApiResponse;

import fr.inria.atlanmod.commons.log.Log;

import java.io.InputStream;
import static java.util.Objects.nonNull;
import java.util.Map;

/**
 * A generic REST GET action.
 * <p>
 * This class extends the generic {@link RestAction} and provides an utility constructor to create GET request.
 *
 * @param <T> the {@link RuntimePlatform} subclass containing this {@link RestGetAction}
 */
public abstract class GetRestRequest<T extends RuntimePlatform> extends RestRequest<T> {

    /**
     * Constructs a new {@link RestGetAction}.
     * <p>
     * This method doesn't perform the REST API request, this is done asynchronously in the {@link #compute()}
     * method.
     *
     * @param runtimePlatform the {@link RuntimePlatform} containing this action
     * @param session         the {@link XatkitSession} associated to this action
     * @param headers         the {@link Map} of user-defined headers to include in the request
     * @param restEndpoint    the REST API endpoint to request
     * @param params          the {@link Map} of user-defined parameters to include in the request
     * @throws NullPointerException     if the provided {@code runtimePlatform} or {@code session} is {@code null}
     * @throws IllegalArgumentException if the provided {@code restEndpoint} is {@code null} or {@code empty}
     */
    public GetRestRequest(T runtimePlatform, XatkitSession session, String restEndpoint, Map<String, Object> queryParams, Object requestBody, Map<String, String> headers, Map<String, Object> formParams,
                         Map<String, Object> pathParams) {
        super(runtimePlatform, session, MethodKind.GET, restEndpoint, queryParams, requestBody, headers,formParams,pathParams);
      
    }
    
    
    
    @Override
    protected final ApiResponse<?> compute() throws Exception {
    	HttpRequest request = Unirest.get(restEndpoint);
    	if(nonNull(headers) && !headers.isEmpty())
    			request.headers(headers);
    	if(nonNull(queryParameters) && !queryParameters.isEmpty())
    		request.queryString(queryParameters);
    	if(nonNull(pathParameters) && !pathParameters.isEmpty())
    		pathParameters.forEach((k,v)-> request.routeParam(k, v.toString()));
    	if(nonNull(formParameters) && !formParameters.isEmpty())
    		((HttpRequestWithBody)request).fields(formParameters);
    	if(nonNull(requestBody))
    		((HttpRequestWithBody)request).body(requestBody.toString());
         
    	Log.info("Sent GET query on {0}", request.getUrl());
    
        HttpResponse<InputStream> response = request.asBinary();
        return this.handleResponse(response.getHeaders(), response.getStatus(), response.getStatusText(), response.getBody());
    }

}
