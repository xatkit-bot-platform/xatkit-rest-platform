package com.xatkit.plugins.rest.platform;

import java.util.Map;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;

import com.xatkit.core.XatkitCore;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.plugins.rest.platform.utils.Helpers;

public class RestPlatform extends RuntimePlatform {

	public static String DEFAULT_QUERY_PARAMETERS_PROPERTY = "rest.platform.default.query.parameters";

	public static String DEFAULT_HEADERS_PROPERTY = "rest.platform.default.headers";

	private Map<String, Object> defaultQueryParameters;

	private Map<String, String> defaultHeaders;

	public RestPlatform(XatkitCore xatkitCore, Configuration configuration) {
		super(xatkitCore, configuration);

		String rawDefaultQueryParameters = configuration.getString(DEFAULT_QUERY_PARAMETERS_PROPERTY);
		if (StringUtils.isNoneBlank(rawDefaultQueryParameters)) {
			defaultQueryParameters = Helpers.parseQueryParameters(rawDefaultQueryParameters);
		}
		String rawDefaultHeaders = configuration.getString(DEFAULT_HEADERS_PROPERTY);
		if (StringUtils.isNoneBlank(rawDefaultHeaders)) {
			defaultHeaders = Helpers.parseHeaders(rawDefaultHeaders);
		}

	}

	public Map<String, Object> getDefaultQueryParameters() {
		return defaultQueryParameters;
	}

	public Map<String, String> getDefaultHeaders() {
		return defaultHeaders;
	}

}
