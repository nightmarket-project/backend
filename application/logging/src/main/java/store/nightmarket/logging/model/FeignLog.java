package store.nightmarket.logging.model;

import java.util.Map;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import store.nightmarket.logging.CommunicationLog;

@Getter
@SuperBuilder
public class FeignLog extends CommunicationLog {
	private final String uri;
	private final String method;
	private final int status;
	private final long responseTimeMs;
	private final Map<String, String> requestHeaders;
	private final Map<String, String> responseHeaders;
	private final String requestBody;
	private final String responseBody;
}