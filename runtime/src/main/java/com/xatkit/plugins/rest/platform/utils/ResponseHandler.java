package com.xatkit.plugins.rest.platform.utils;

import java.io.InputStream;


/**
 * A {@link FunctionalInterface} to handle the response returned by a request for the target element <T>
 *
 * @param <T> the concrete type of the response
 */
@FunctionalInterface
public interface ResponseHandler<T> {

    /**
     * Transform an {@link InputStream} instance to a target type
     *
     * @param body body of the response as an {@link InputStream} instance
     * @return decoded response in the target format
     */
    T handleResponse(InputStream body);

}
