package com.xatkit.plugins.rest.platform.utils;

import java.io.InputStream;

import com.mashape.unirest.http.Headers;

@FunctionalInterface
public interface ResponseHandler<T> {
	
	   /**
     * Handles the REST API response and computes the action's results
     * <p>
     * This method is overridden in concrete subclasses to implement action-specific behaviors.
     *
     * @param headers     the {@link Headers} returned by the REST API
     * @param statusCode      the status code returned by the REST API
     * @param statusText      the status text returned by the REST API
     * @param body the {@link InputStream} containing the response body
     * @return the action's result
     */
	ApiResponse<T> handleResponse(Headers headers, int statusCode, String statusText, InputStream body);

}
