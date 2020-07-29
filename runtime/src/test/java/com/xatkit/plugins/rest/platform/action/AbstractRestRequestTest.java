package com.xatkit.plugins.rest.platform.action;

import com.xatkit.AbstractActionTest;
import com.xatkit.plugins.rest.platform.RestPlatform;

public abstract class AbstractRestRequestTest<R extends RestRequest<?, ?>> extends AbstractActionTest<R, RestPlatform> {

    /*
     * Not used for the moment.
     */
//    protected static String VALID_POST_ENDPOINT = "http://httpbin.org/post";

    @Override
    protected RestPlatform getPlatform() {
        return new RestPlatform();
    }
}