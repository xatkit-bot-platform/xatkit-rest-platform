package com.xatkit.plugins.rest.platform;

import com.xatkit.core.XatkitCore;
import com.xatkit.core.platform.RuntimePlatform;
import lombok.Getter;
import org.apache.commons.configuration2.Configuration;

public class RestPlatform extends RuntimePlatform {

    @Getter
    private RestPlatformConfiguration restPlatformConfiguration;

    public RestPlatform(XatkitCore xatkitCore, Configuration configuration) {
        super(xatkitCore, configuration);
        this.restPlatformConfiguration = new RestPlatformConfiguration(configuration);
    }
}
