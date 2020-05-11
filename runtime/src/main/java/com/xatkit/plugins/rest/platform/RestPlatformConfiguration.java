package com.xatkit.plugins.rest.platform;

import com.google.common.base.Splitter;
import lombok.NonNull;
import lombok.Value;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contains {@link RestPlatform}-related configuration.
 * <p>
 * This class can be initialized with a {@link Configuration} instance, and takes care of extracting the
 * {@link RestPlatform}-related properties.
 * <p>
 * The base {@link Configuration} used to initialize this class can be accessed through {@link #getBaseConfiguration()}.
 */
@Value
public class RestPlatformConfiguration {

    /**
     * The {@link Configuration} key to store the default parameters to add to the sent REST requests.
     * <p>
     * This parameter is typically used to store API keys that need to be included in the REST request parameters.
     * <p>
     * The value associated to this key must match the following pattern: {@code param1=value1&param2=value2}.
     *
     * @see #getDefaultQueryParameters()
     */
    public static String DEFAULT_QUERY_PARAMETERS_PROPERTY = "xatkit.rest.default.query.parameters";

    /**
     * The {@link Configuration} key used to store the default headers to add to the sent REST requests.
     * <p>
     * This parameter is typically used to store API keys that need to be included in the REST request headers.
     * <p>
     * The value associated to this key must match the following pattern: {@code headerName1:value1&headerName2:value2}.
     *
     * @see #getDefaultHeaders()
     */
    public static String DEFAULT_HEADERS_PROPERTY = "xatkit.rest.default.headers";

    /**
     * The default query parameters to include in all the REST requests performed by the platform.
     * <p>
     * This attribute is initialized with {@link Collections#emptyMap()} if the property is not specified.
     *
     * @see #DEFAULT_QUERY_PARAMETERS_PROPERTY
     */
    private Map<String, Object> defaultQueryParameters;

    /**
     * The default headers to include in all the REST requests performed by the platform.
     * <p>
     * This attribute is initialized with {@link Collections#emptyMap()} if the property is not specified.
     *
     * @see #DEFAULT_HEADERS_PROPERTY
     */
    private Map<String, String> defaultHeaders;

    /**
     * The base {@link Configuration} used to initialize this {@link RestPlatformConfiguration}.
     */
    private Configuration baseConfiguration;

    /**
     * Initializes the {@link RestPlatformConfiguration} with the provided {@code Configuration}.
     *
     * @param baseConfiguration the {@link Configuration} to load the values from
     * @throws NullPointerException if the provided {@code baseConfiguration} is {@code null}
     */
    public RestPlatformConfiguration(@NonNull Configuration baseConfiguration) {
        this.baseConfiguration = baseConfiguration;
        this.defaultQueryParameters = computeDefaultQueryParameters(baseConfiguration);
        this.defaultHeaders = computeDefaultHeaders(baseConfiguration);

    }

    /**
     * Builds a {@link Map} containing the default query parameters specified in the provided {@code configuration}.
     * <p>
     * The configuration value associated to {@link #DEFAULT_QUERY_PARAMETERS_PROPERTY} must match the following
     * pattern: {@code param1=value1&param2=value2}.
     *
     * @param configuration the {@link Configuration} to retrieve the default query parameters from
     * @return the {@link Map} containing the extracted parameters, or a {@link Collections#emptyMap()} if the
     * property is not specified.
     * @throws IllegalArgumentException if the configuration value does not match the required pattern
     */
    private Map<String, Object> computeDefaultQueryParameters(Configuration configuration) {
        String rawDefaultQueryParameters = configuration.getString(DEFAULT_QUERY_PARAMETERS_PROPERTY);
        if (StringUtils.isNoneBlank(rawDefaultQueryParameters)) {
            return Splitter.on("&").withKeyValueSeparator("=").split(rawDefaultQueryParameters).entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            return Collections.emptyMap();
        }
    }

    /**
     * Builds a {@link Map} containing the default headers specified in the provided {@code configuration}.
     * <p>
     * The configuration value associated to {@link #DEFAULT_HEADERS_PROPERTY} must match the following pattern:
     * {@code headerName1:value1&headerName2:value2}.
     *
     * @param configuration the {@link Configuration} to retrieve the default headers from
     * @return the {@link Map} containing the extracted headers, or a {@link Collections#emptyMap()} if the property
     * is not specified.
     * @throws IllegalArgumentException if the configuration value does not match the required pattern
     */
    private Map<String, String> computeDefaultHeaders(Configuration configuration) {
        String rawDefaultHeaders = configuration.getString(DEFAULT_HEADERS_PROPERTY);
        if (StringUtils.isNoneBlank(rawDefaultHeaders)) {
            return Splitter.on("&").withKeyValueSeparator(":").split(rawDefaultHeaders);
        } else {
            return Collections.emptyMap();
        }
    }
}
