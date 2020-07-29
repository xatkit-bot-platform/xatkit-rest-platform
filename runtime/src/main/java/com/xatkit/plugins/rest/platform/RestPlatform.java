package com.xatkit.plugins.rest.platform;

import com.google.gson.JsonElement;
import com.xatkit.core.XatkitCore;
import com.xatkit.core.platform.RuntimePlatform;
import com.xatkit.core.platform.action.RuntimeActionResult;
import com.xatkit.execution.StateContext;
import com.xatkit.plugins.rest.platform.action.DeleteJsonRequest;
import com.xatkit.plugins.rest.platform.action.DeleteJsonRequestWithBody;
import com.xatkit.plugins.rest.platform.action.DeleteJsonRequestWithFormData;
import com.xatkit.plugins.rest.platform.action.GetJsonRequest;
import com.xatkit.plugins.rest.platform.action.GetJsonRequestWithBody;
import com.xatkit.plugins.rest.platform.action.GetJsonRequestWithFormData;
import com.xatkit.plugins.rest.platform.action.PostJsonRequestWithBody;
import com.xatkit.plugins.rest.platform.action.PostJsonRequestWithFormData;
import com.xatkit.plugins.rest.platform.action.PutJsonRequestWithBody;
import com.xatkit.plugins.rest.platform.action.PutJsonRequestWithFormData;
import com.xatkit.plugins.rest.platform.utils.ApiResponse;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.configuration2.Configuration;

import javax.annotation.Nullable;
import java.util.Map;

public class RestPlatform extends RuntimePlatform {

    @Getter
    private RestPlatformConfiguration restPlatformConfiguration;

    public RestPlatform() {
        super();
    }

    @Override
    public void start(@NonNull XatkitCore xatkitCore, @NonNull Configuration configuration) {
        super.start(xatkitCore, configuration);
        this.restPlatformConfiguration = new RestPlatformConfiguration(configuration);
    }

    /**
     * Sends a DELETE REST action to the given {@code restEndpoint}.
     *
     * @param context      the current {@link StateContext}
     * @param restEndpoint the endpoint to send the request to
     * @param queryParams  a {@link Map} containing the parameters of the request
     * @param pathParams   a {@link Map} containing the path parameters of the request
     * @param headers      a {@link Map} containing the headers of the request
     * @return the {@link ApiResponse}
     */
    public @NonNull ApiResponse<JsonElement> deleteJsonRequest(@NonNull StateContext context,
                                                               @NonNull String restEndpoint,
                                                               @Nullable Map<String, Object> queryParams,
                                                               @Nullable Map<String, String> pathParams,
                                                               @Nullable Map<String, String> headers) {
        DeleteJsonRequest action = new DeleteJsonRequest(this, context, restEndpoint, queryParams, pathParams, headers);
        RuntimeActionResult result = this.executeRuntimeAction(action);
        return (ApiResponse<JsonElement>) result.getResult();
    }

    /**
     * Sends a DELETE REST action to the given {@code restEndpoint} with the given {@code requestBody}.
     *
     * @param context      the current {@link StateContext}
     * @param restEndpoint the endpoint to send the request to
     * @param queryParams  a {@link Map} containing the parameters of the request
     * @param pathParams   a {@link Map} containing the path parameters of the request
     * @param requestBody  the body of the request
     * @param headers      a {@link Map} containing the headers of the request
     * @return the {@link ApiResponse}
     */
    public @NonNull ApiResponse<JsonElement> deleteJsonRequestWithBody(@NonNull StateContext context,
                                                              @NonNull String restEndpoint,
                                                              @Nullable Map<String, Object> queryParams,
                                                              @Nullable Map<String, String> pathParams,
                                                              @Nullable JsonElement requestBody,
                                                              @Nullable Map<String, String> headers) {
        DeleteJsonRequestWithBody action = new DeleteJsonRequestWithBody(this, context, restEndpoint, queryParams,
                pathParams, requestBody, headers);
        RuntimeActionResult result = this.executeRuntimeAction(action);
        return (ApiResponse<JsonElement>) result.getResult();
    }

    /**
     * Sends a DELETE REST action to the given {@code restEndpoint} with the given {@code formParams}.
     *
     * @param context      the current {@link StateContext}
     * @param restEndpoint the endpoint to send the request to
     * @param queryParams  a {@link Map} containing the parameters of the request
     * @param pathParams   a {@link Map} containing the path parameters of the request
     * @param headers      a {@link Map} containing the headers of the request
     * @param formParams   the form data of the request
     * @return the {@link ApiResponse}
     */
    public @NonNull ApiResponse<JsonElement> deleteJsonRequestWithFormData(@NonNull StateContext context,
                                                                  @NonNull String restEndpoint,
                                                                  @Nullable Map<String, Object> queryParams,
                                                                  @Nullable Map<String, String> pathParams,
                                                                  @Nullable Map<String, String> headers,
                                                                  @Nullable Map<String, Object> formParams) {
        DeleteJsonRequestWithFormData action = new DeleteJsonRequestWithFormData(this, context, restEndpoint,
                queryParams, pathParams, headers, formParams);
        RuntimeActionResult result = this.executeRuntimeAction(action);
        return (ApiResponse<JsonElement>) result.getResult();
    }

    /**
     * Sends a GET REST action to the given {@code restEndpoint}.
     *
     * @param context      the current {@link StateContext}
     * @param restEndpoint the endpoint to send the request to
     * @param queryParams  a {@link Map} containing the parameters of the request
     * @param pathParams   a {@link Map} containing the path parameters of the request
     * @param headers      a {@link Map} containing the headers of the request
     * @return the {@link ApiResponse}
     */
    public @NonNull ApiResponse<JsonElement> getJsonRequest(@NonNull StateContext context,
                                                            @NonNull String restEndpoint,
                                                            @Nullable Map<String, Object> queryParams,
                                                            @Nullable Map<String, String> pathParams,
                                                            @Nullable Map<String, String> headers) {
        GetJsonRequest action = new GetJsonRequest(this, context, restEndpoint, queryParams, pathParams, headers);
        RuntimeActionResult result = this.executeRuntimeAction(action);
        return (ApiResponse<JsonElement>) result.getResult();
    }

    /**
     * Sends a GET REST action to the given {@code restEndpoint} with the given {@code requestBody}.
     *
     * @param context      the current {@link StateContext}
     * @param restEndpoint the endpoint to send the request to
     * @param queryParams  a {@link Map} containing the parameters of the request
     * @param pathParams   a {@link Map} containing the path parameters of the request
     * @param requestBody  the body of the request
     * @param headers      a {@link Map} containing the headers of the request
     * @return the {@link ApiResponse}
     */
    public @NonNull ApiResponse<JsonElement> getJsonRequestWithBody(@NonNull StateContext context,
                                                                    @NonNull String restEndpoint,
                                                                    @Nullable Map<String, Object> queryParams,
                                                                    @Nullable Map<String, String> pathParams,
                                                                    @Nullable JsonElement requestBody,
                                                                    @Nullable Map<String, String> headers) {
        GetJsonRequestWithBody action = new GetJsonRequestWithBody(this, context, restEndpoint, queryParams,
                pathParams, requestBody, headers);
        RuntimeActionResult result = this.executeRuntimeAction(action);
        return (ApiResponse<JsonElement>) result.getResult();
    }

    /**
     * Sends a GET REST action to the given {@code restEndpoint} with the given {@code formParams}.
     *
     * @param context      the current {@link StateContext}
     * @param restEndpoint the endpoint to send the request to
     * @param queryParams  a {@link Map} containing the parameters of the request
     * @param pathParams   a {@link Map} containing the path parameters of the request
     * @param headers      a {@link Map} containing the headers of the request
     * @param formParams   the form data of the request
     * @return the {@link ApiResponse}
     */
    public @NonNull ApiResponse<JsonElement> getJsonRequestWithFormData(@NonNull StateContext context,
                                                                        @NonNull String restEndpoint,
                                                                        @Nullable Map<String, Object> queryParams,
                                                                        @Nullable Map<String, String> pathParams,
                                                                        @Nullable Map<String, String> headers,
                                                                        @Nullable Map<String, Object> formParams) {
        GetJsonRequestWithFormData action = new GetJsonRequestWithFormData(this, context, restEndpoint,
                queryParams, pathParams, headers, formParams);
        RuntimeActionResult result = this.executeRuntimeAction(action);
        return (ApiResponse<JsonElement>) result.getResult();
    }

    /**
     * Sends a POST REST action to the given {@code restEndpoint} with the given {@code requestBody}.
     *
     * @param context      the current {@link StateContext}
     * @param restEndpoint the endpoint to send the request to
     * @param queryParams  a {@link Map} containing the parameters of the request
     * @param pathParams   a {@link Map} containing the path parameters of the request
     * @param requestBody  the body of the request
     * @param headers      a {@link Map} containing the headers of the request
     * @return the {@link ApiResponse}
     */
    public @NonNull ApiResponse<JsonElement> postJsonRequestWithBody(@NonNull StateContext context,
                                                                     @NonNull String restEndpoint,
                                                                     @Nullable Map<String, Object> queryParams,
                                                                     @Nullable Map<String, String> pathParams,
                                                                     @Nullable JsonElement requestBody,
                                                                     @Nullable Map<String, String> headers) {
        PostJsonRequestWithBody action = new PostJsonRequestWithBody(this, context, restEndpoint, queryParams,
                pathParams, requestBody, headers);
        RuntimeActionResult result = this.executeRuntimeAction(action);
        return (ApiResponse<JsonElement>) result.getResult();
    }

    /**
     * Sends a POST REST action to the given {@code restEndpoint} with the given {@code formParams}.
     *
     * @param context      the current {@link StateContext}
     * @param restEndpoint the endpoint to send the request to
     * @param queryParams  a {@link Map} containing the parameters of the request
     * @param pathParams   a {@link Map} containing the path parameters of the request
     * @param headers      a {@link Map} containing the headers of the request
     * @param formParams   the form data of the request
     * @return the {@link ApiResponse}
     */
    public @NonNull ApiResponse<JsonElement> postJsonRequestWithFormData(@NonNull StateContext context,
                                                                         @NonNull String restEndpoint,
                                                                         @Nullable Map<String, Object> queryParams,
                                                                         @Nullable Map<String, String> pathParams,
                                                                         @Nullable Map<String, String> headers,
                                                                         @Nullable Map<String, Object> formParams) {
        PostJsonRequestWithFormData action = new PostJsonRequestWithFormData(this, context, restEndpoint,
                queryParams, pathParams, headers, formParams);
        RuntimeActionResult result = this.executeRuntimeAction(action);
        return (ApiResponse<JsonElement>) result.getResult();
    }

    /**
     * Sends a PUT REST action to the given {@code restEndpoint} with the given {@code requestBody}.
     *
     * @param context      the current {@link StateContext}
     * @param restEndpoint the endpoint to send the request to
     * @param queryParams  a {@link Map} containing the parameters of the request
     * @param pathParams   a {@link Map} containing the path parameters of the request
     * @param requestBody  the body of the request
     * @param headers      a {@link Map} containing the headers of the request
     * @return the {@link ApiResponse}
     */
    public @NonNull ApiResponse<JsonElement> putJsonRequestWithBody(@NonNull StateContext context,
                                                                    @NonNull String restEndpoint,
                                                                    @Nullable Map<String, Object> queryParams,
                                                                    @Nullable Map<String, String> pathParams,
                                                                    @Nullable JsonElement requestBody,
                                                                    @Nullable Map<String, String> headers) {
        PutJsonRequestWithBody action = new PutJsonRequestWithBody(this, context, restEndpoint, queryParams,
                pathParams, requestBody, headers);
        RuntimeActionResult result = this.executeRuntimeAction(action);
        return (ApiResponse<JsonElement>) result.getResult();
    }

    /**
     * Sends a PUT REST action to the given {@code restEndpoint} with the given {@code formParams}.
     *
     * @param context      the current {@link StateContext}
     * @param restEndpoint the endpoint to send the request to
     * @param queryParams  a {@link Map} containing the parameters of the request
     * @param pathParams   a {@link Map} containing the path parameters of the request
     * @param headers      a {@link Map} containing the headers of the request
     * @param formParams   the form data of the request
     * @return the {@link ApiResponse}
     */
    public @NonNull ApiResponse<JsonElement> putJsonRequestWithFormData(@NonNull StateContext context,
                                                                        @NonNull String restEndpoint,
                                                                        @Nullable Map<String, Object> queryParams,
                                                                        @Nullable Map<String, String> pathParams,
                                                                        @Nullable Map<String, String> headers,
                                                                        @Nullable Map<String, Object> formParams) {
        PutJsonRequestWithFormData action = new PutJsonRequestWithFormData(this, context, restEndpoint,
                queryParams, pathParams, headers, formParams);
        RuntimeActionResult result = this.executeRuntimeAction(action);
        return (ApiResponse<JsonElement>) result.getResult();
    }
}
