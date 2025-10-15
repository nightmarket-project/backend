package store.nightmarket.logging.model.detail;

import java.util.Map;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import store.nightmarket.logging.model.CommunicationLog;

@Getter
@SuperBuilder
public class AccessLog extends CommunicationLog {
	private final String uri;
	private final String method;
	private final int status;
	private final long responseTimeMs;
	private final Map<String, String> requestHeaders;
	private final Map<String, String> responseHeaders;
	private final String requestBody;
	private final String responseBody;
}