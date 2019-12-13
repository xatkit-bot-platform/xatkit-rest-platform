package com.xatkit.plugins.rest.platform;


import com.xatkit.Xatkit;

/**
 * This class is used to run existing bots, and should not contain test cases.
 */
public class RestBotTest {

    public static void main(String[] args) throws Exception {
        Xatkit.main(new String[]{"C:\\Users\\hamza\\git\\xatkit-rest-platform\\example\\RestBotExample.properties"});
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
