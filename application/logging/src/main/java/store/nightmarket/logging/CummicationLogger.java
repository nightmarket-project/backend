package store.nightmarket.logging;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.JacksonUtils;

import net.logstash.logback.argument.StructuredArguments;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CummicationLogger {

	private static final Logger log = LoggerFactory.getLogger(CummicationLogger.class);
	private static final ObjectMapper objectMapper = JacksonUtils.enhancedObjectMapper();

	public static void log(CommunicationLog communicationLog) {
		Map<String, Object> map = objectMapper.convertValue(communicationLog, new TypeReference<>() {
		});
		log.info("{} {}", communicationLog.getEventType().toString(), StructuredArguments.value("event", map));
	}

}
