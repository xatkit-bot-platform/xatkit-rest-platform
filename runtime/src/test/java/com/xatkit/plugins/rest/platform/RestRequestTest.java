package com.xatkit.plugins.rest.platform;

import org.apache.commons.configuration2.BaseConfiguration;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.xatkit.AbstractXatkitTest;
import com.xatkit.core.XatkitCore;
import com.xatkit.core.session.XatkitSession;
import com.xatkit.stubs.StubXatkitCore;

public abstract class RestRequestTest extends AbstractXatkitTest {

    protected static XatkitCore XATKIT_CORE;

    protected static RestPlatform RUNTIME_PLATFORM;


    protected static String VALID_GET_ENDPOINT = "http://httpbin.org/get";
    
    protected static String VALID_POST_ENDPOINT = "http://httpbin.org/post";

   

    


    protected XatkitSession session;

    @BeforeClass
    public static void setUpBeforeClass() {
        XATKIT_CORE = new StubXatkitCore();
        RUNTIME_PLATFORM = new RestPlatform(XATKIT_CORE, new BaseConfiguration());
    }

    @AfterClass
    public static void tearDownAfterClass() {
        RUNTIME_PLATFORM.shutdown();
        XATKIT_CORE.shutdown();
    }

    @Before
    public void setUp() {
        this.session = new XatkitSession("sessionID");
    }
}