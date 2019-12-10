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

public class GetRequestJson extends GetRestRequest<RestPlatform> {

	public GetRequestJson(RestPlatform runtimePlatform, XatkitSession session, String restEndpoint,
			Map<String, Object> queryParams, Object requestBody, Map<String, String> headers,
			Map<String, Object> formParams, Map<String, Object> pathParams) {
		super(runtimePlatform, session, restEndpoint, queryParams, requestBody, headers, formParams, pathParams);
	}

	@Override
	protected final ApiResponse<JsonElement> handleResponse(Headers headers, int statusCode, String statusText,
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