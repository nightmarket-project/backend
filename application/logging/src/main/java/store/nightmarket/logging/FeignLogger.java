package store.nightmarket.logging;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import store.nightmarket.logging.model.FeignLog;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class FeignLogger extends Logger {

	@Override
	protected void log(String configKey, String format, Object... args) {
	}

	@Override
	protected void logRequest(String configKey, Level logLevel, Request request) {
	}

	@Override
	protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime)
		throws IOException {

		byte[] requestBodyData = response.request().body();

		byte[] responseBodyData = (response.body() == null) ?
			null : Util.toByteArray(response.body().asInputStream());

		String requestBody = parseBody(requestBodyData, response.request().charset());
		String responseBody = parseBody(responseBodyData, response.charset());

		FeignLog logData = FeignLog.builder()
			.eventType(CommunicationLog.Type.FEIGN)
			.uri(response.request().url())
			.method(response.request().httpMethod().name())
			.status(response.status())
			.responseTimeMs(elapsedTime)
			.requestHeaders(extractHeaders(response.request().headers()))
			.responseHeaders(extractHeaders(response.headers()))
			.requestBody(requestBody)
			.responseBody(responseBody)
			.build();

		CummicationLogger.log(logData);

		return response.toBuilder().body(responseBodyData).build();
	}

	private Map<String, String> extractHeaders(Map<String, Collection<String>> headers) {
		if (headers == null)
			return Collections.emptyMap();
		return headers.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, e -> String.join(",", e.getValue())));
	}

	private String parseBody(byte[] data, Charset charset) {
		if (data == null || data.length == 0)
			return null;
		return new String(data, charset == null ? StandardCharsets.UTF_8 : charset);
	}

}

