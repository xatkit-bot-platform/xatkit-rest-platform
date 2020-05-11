package com.xatkit.plugins.rest.platform;

import com.xatkit.core.XatkitCore;
import com.xatkit.core.platform.RuntimePlatform;
import lombok.Getter;
import org.apache.commons.configuration2.Configuration;

public class RestPlatform extends RuntimePlatform {

    @Getter
    private RestPlatformConfiguration configuration;

    public RestPlatform(XatkitCore xatkitCore, Configuration configuration) {
        super(xatkitCore, configuration);
        this.configuration = new RestPlatformConfiguration(configuration);
    }
}
