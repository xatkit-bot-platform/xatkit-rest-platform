package com.xatkit.plugins.restplatform;


import com.xatkit.Xatkit;

/**
 * This class is used to run existing bots, and should not contain test cases.
 */
public class RestBotTest {

    public static void main(String[] args) throws Exception {
        Xatkit.main(new String[]{"<path/to/properties>"});
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
