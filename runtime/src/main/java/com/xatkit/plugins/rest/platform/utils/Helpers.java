package com.xatkit.plugins.rest.platform.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Splitter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import fr.inria.atlanmod.commons.log.Log;

public class Helpers {
	
	
	   
    public static Map<String, Object> parseQueryParameters(String formattedMap) {
        return Splitter.on(";").withKeyValueSeparator("=").split(formattedMap).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    public static JsonElement parseJson(InputStream inputStream) {
    	JsonElement jsonResponse  = null;
    		try {
    			jsonResponse = new JsonParser().parse(new InputStreamReader(inputStream));
    		}
    		catch (JsonParseException e) {
    			Log.error(e, "Can't parse the response. See the attached exception");
    		}
    	
		return jsonResponse;
    }
   

}
