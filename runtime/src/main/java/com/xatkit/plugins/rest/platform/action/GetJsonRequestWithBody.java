package com.xatkit.plugins.rest.platform.action;



import static java.util.Objects.nonNull;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import com.google.gson.JsonElement;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.rest.platform.RestPlatform;
import com.xatkit.plugins.rest.platform.utils.ApiResponse;

import fr.inria.atlanmod.commons.log.Log;


public class GetJsonRequestWithBody extends JsonRestRequest {


    public GetJsonRequestWithBody(RestPlatform runtimePlatform, XatkitSession session, String restEndpoint, Map<String, Object> queryParams, Map<String, Object> pathParams, JsonElement requestBody, Map<String, String> headers ) {
        super(runtimePlatform, session, MethodKind.GET, restEndpoint, queryParams, pathParams,requestBody, headers,Collections.emptyMap());
      
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