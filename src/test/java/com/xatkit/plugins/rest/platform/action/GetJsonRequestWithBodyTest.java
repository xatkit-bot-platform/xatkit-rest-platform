package com.xatkit.plugins.rest.platform.action;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xatkit.plugins.rest.platform.utils.ApiResponse;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class GetJsonRequestWithBodyTest extends AbstractRestRequestTest<GetJsonRequestWithBody> {

    protected String VALID_GET_ENDPOINT = "http://httpbin.org/get";

    @Test
    public void computeValidGetEndpoint() throws Exception {
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("key", "value");
        GetJsonRequestWithBody getJsonRequestWithBody = new GetJsonRequestWithBody(platform, context,
                VALID_GET_ENDPOINT, Collections.emptyMap(), Collections.emptyMap(), jsonBody, Collections.emptyMap());
        ApiResponse<JsonElement> response = getJsonRequestWithBody.compute();
        assertThat(response.getStatus()).as("Valid response status").isEqualTo(200);
        assertThat(response.getBody()).as("Valid JsonElement").isInstanceOf(JsonElement.class);
        assertThat(response.getHeaders()).as("Valid response Headers").isNotNull();
    }
}
