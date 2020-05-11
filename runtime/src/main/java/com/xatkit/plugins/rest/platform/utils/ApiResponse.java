package com.xatkit.plugins.rest.platform.utils;

import java.util.List;
import java.util.Map;


/**
 * An object to hold the content of an HTTP response. This object is returned after computing an action
 *
 * @param <T> an object holding the body of the response
 */
public class ApiResponse<T> {

    /**
     * The headers of the response
     */
    private Map<String, List<String>> headers;

    /**
     * The status code of the response
     */
    private int status;

    /**
     * The status text of the response
     */
    private String statusText;

    /**
     * The body of the response
     */
    private T body;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

}