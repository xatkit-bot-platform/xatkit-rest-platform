package com.xatkit.plugins.rest.platform.action;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.Headers;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.plugins.rest.platform.RestPlatform;
import com.xatkit.plugins.rest.platform.utils.ApiResponse;
import com.xatkit.plugins.rest.platform.utils.ResponseHandler;

public abstract class JsonRestRequest extends RestRequest<JsonElement>  implements ResponseHandler<JsonElement>  {

	public JsonRestRequest(RestPlatform runtimePlatform, XatkitSession session, MethodKind method, String restEndpoint,
			Map<String, Object> queryParameters, Map<String, Object> pathParameters, JsonElement requestBody,
			Map<String, String> headers, Map<String, Object> formParameters) {
		super(runtimePlatform, session, method, restEndpoint, queryParameters, pathParameters, requestBody, headers,
				formParameters);
		
	}

	@Override
	public ApiResponse<JsonElement> handleResponse(Headers headers, int statusCode, String statusText,
			InputStream body) {
		ApiResponse<JsonElement> apiResponse = new ApiResponse<JsonElement>();

		JsonElement jsonResponse = new JsonParser().parse(new InputStreamReader(body));
		apiResponse.setBody(jsonResponse);
		apiResponse.setHeaders(((Map<String, List<String>>) headers));
		apiResponse.setStatus(statusCode);
		apiResponse.setStatusText(statusText);

		return apiResponse;
	}
	
	

}
