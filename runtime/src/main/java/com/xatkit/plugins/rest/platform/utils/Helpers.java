package com.xatkit.plugins.rest.platform.utils;

import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;

public class Helpers {
	
	
	   
    public static Map<String, Object> parseQueryParameters(String formattedMap) {
        return Splitter.on(";").withKeyValueSeparator("=").split(formattedMap).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
   

}
