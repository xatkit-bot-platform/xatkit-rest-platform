package com.xatkit.plugins.rest.platform.action;

import com.xatkit.AbstractActionTest;
import com.xatkit.plugins.rest.platform.RestPlatform;
import org.apache.commons.configuration2.BaseConfiguration;

public abstract class AbstractRestRequestTest<R extends RestRequest<?, ?>> extends AbstractActionTest<R, RestPlatform> {

    /*
     * Not used for the moment.
     */
//    protected static String VALID_POST_ENDPOINT = "http://httpbin.org/post";

    @Override
    protected RestPlatform getPlatform() {
        RestPlatform restPlatform = new RestPlatform();
        restPlatform.start(mockedXatkitBot, new BaseConfiguration());
        return restPlatform;
    }
}