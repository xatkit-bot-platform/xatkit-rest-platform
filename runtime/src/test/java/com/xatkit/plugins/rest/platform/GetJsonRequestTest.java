package com.xatkit.plugins.rest.platform;

import java.util.Collections;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xatkit.plugins.rest.platform.action.GetJsonRequest;
import com.xatkit.plugins.rest.platform.utils.ApiResponse;

import static org.assertj.core.api.Assertions.assertThat;


public class GetJsonRequestTest extends RestRequestTest {

	@Test
	public void constractValidGetJsonRequest() {
		new GetJsonRequest(RUNTIME_PLATFORM, session, VALID_GET_ENDPOINT, Collections.emptyMap(),
				Collections.emptyMap(), Collections.emptyMap());
	}

	@Test
	public void constructNullHeaders() {
		new GetJsonRequest(RUNTIME_PLATFORM, session, VALID_GET_ENDPOINT, Collections.emptyMap(),
				Collections.emptyMap(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructNullEndpoint() {
		new GetJsonRequest(RUNTIME_PLATFORM, session, null, Collections.emptyMap(), Collections.emptyMap(),
				Collections.emptyMap());
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructEmptyEndpoint() {
		new GetJsonRequest(RUNTIME_PLATFORM, session, "", Collections.emptyMap(), Collections.emptyMap(),
				Collections.emptyMap());
	}

	@Test
	public void constructNullParams() {
		new GetJsonRequest(RUNTIME_PLATFORM, session, VALID_GET_ENDPOINT, null, null, null);

	}

	@Test
	public void computeValidGetEndpoint() throws Exception {
		GetJsonRequest getJsonRequest = new GetJsonRequest(RUNTIME_PLATFORM, session, VALID_GET_ENDPOINT,
				Collections.emptyMap(), Collections.emptyMap(), Collections.emptyMap());
		
		ApiResponse<JsonElement> response = getJsonRequest.compute();
		assertThat(response.getStatus()).as("Valid response status").isEqualTo(200);
		assertThat(response.getBody()).as("Valid JsonElement").isInstanceOf(JsonElement.class);
		assertThat(response.getHeaders()).as("Valid response Headers").isNotNull();
	}
	
	@Test
	public void computeValidGetRequestWithQueryParameters() throws Exception {
		GetJsonRequest getJsonRequest = new GetJsonRequest(RUNTIME_PLATFORM, session, VALID_GET_ENDPOINT,ImmutableMap.<String,Object>builder().put("foo","bar").build(), Collections.emptyMap(), Collections.emptyMap());
		
		ApiResponse<JsonElement> response = getJsonRequest.compute();
		assertThat(response.getStatus()).as("Valid response status").isEqualTo(200);
		JsonObject args = response.getBody().getAsJsonObject().get("args").getAsJsonObject();
		
		assertThat(args.has("foo")).as("Query parameter correctly added to the request").isTrue();
		assertThat(args.get("foo").getAsString()).as("Query parameter value correctly added to the request").isEqualTo("bar");
	}


}
