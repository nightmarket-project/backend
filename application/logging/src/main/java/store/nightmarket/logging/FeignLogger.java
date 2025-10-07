package store.nightmarket.logging;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import net.logstash.logback.argument.StructuredArguments;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.logging.model.detail.FeignEvent;

@Slf4j
public class FeignLogger extends Logger {

	@Override
	protected void log(String configKey, String format, Object... args) {

	}

	@Override
	protected void logRequest(String configKey, Level logLevel, Request request) {

		FeignEvent event = FeignEvent.builder()
			.eventType("FEIGN")
			.direction("OUT")
			.feignClient(configKey)
			.targetService(extractServiceName(request))
			.uri(request.url())
			.method(request.httpMethod().name())
			.headers(flattenHeaders(request.headers()))
			.body(parseRequestBody(request))
			.build();

		log.info(">>> FEIGN REQUEST", StructuredArguments.value("event", event));
	}

	@Override
	protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response,
		long elapsedTime) throws IOException {
		byte[] bodyData = Util.toByteArray(response.body().asInputStream());

		FeignEvent event = FeignEvent.builder()
			.eventType("FEIGN")
			.direction("IN")
			.feignClient(configKey)
			.targetService(extractServiceName(response.request()))
			.uri(response.request().url())
			.method(response.request().httpMethod().name())
			.headers(flattenHeaders(response.request().headers()))
			.body(parseResponseBody(bodyData, response.charset()))
			.status(response.status())
			.responseTimeMs(elapsedTime)
			.build();

		log.info(">>> FEIGN RESPONSE", StructuredArguments.value("event", event));

		return response.toBuilder().body(bodyData).build();
	}

	private String extractServiceName(Request request) {
		return request.url().split("/")[2]; //ex http://order-service/api/... → order-service
	}

	private Map<String, Object> flattenHeaders(Map<String, Collection<String>> headers) {
		return headers.entrySet().stream()
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				Map.Entry::getValue
			));
	}

	private Object parseRequestBody(Request request) {
		if (request == null || request.body() == null) {
			return null;
		}

		String body = new String(request.body(), request.charset());

		try {
			return new ObjectMapper().readTree(body);
		} catch (Exception e) {
			return body;
		}

	}

	private Object parseResponseBody(byte[] bodyData, Charset charset) {
		String body = new String(bodyData, charset);

		try {
			return new ObjectMapper().readTree(body);
		} catch (Exception e) {
			return body;
		}

	}

}
