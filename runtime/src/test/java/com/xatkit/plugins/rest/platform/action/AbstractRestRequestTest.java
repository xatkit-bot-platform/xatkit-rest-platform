package com.xatkit.plugins.rest.platform;

import com.xatkit.AbstractActionTest;
import com.xatkit.plugins.rest.platform.action.RestRequest;
import org.apache.commons.configuration2.BaseConfiguration;

public abstract class AbstractRestRequestTest<R extends RestRequest<?,?>> extends AbstractActionTest<R, RestPlatform> {


    protected static String VALID_POST_ENDPOINT = "http://httpbin.org/post";

    @Override
    protected RestPlatform getPlatform() {
        return new RestPlatform(mockedXatkitCore, new BaseConfiguration());
    }
}