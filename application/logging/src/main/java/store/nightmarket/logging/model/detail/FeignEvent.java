package store.nightmarket.logging.model.detail;

import java.util.Map;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import store.nightmarket.logging.model.BaseLogEvent;

@Getter
@SuperBuilder
public class FeignEvent extends BaseLogEvent {
	private String feignClient;
	private String targetService;
	private String uri;
	private String method;
	private Map<String, Object> headers;
	private Object body;
	private int status;
	private long responseTimeMs;
}